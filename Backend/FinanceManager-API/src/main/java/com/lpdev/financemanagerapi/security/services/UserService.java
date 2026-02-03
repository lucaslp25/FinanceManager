package com.lpdev.financemanagerapi.security.services;

import com.lpdev.financemanagerapi.exceptions.FinanceManagerBadRequestException;
import com.lpdev.financemanagerapi.exceptions.FinanceManagerConflictException;
import com.lpdev.financemanagerapi.exceptions.FinanceManagerNotFoundException;
import com.lpdev.financemanagerapi.microservices.email.EmailService;
import com.lpdev.financemanagerapi.model.entities.Transaction;
import com.lpdev.financemanagerapi.model.entities.Wallet;
import com.lpdev.financemanagerapi.repositories.WalletRepository;
import com.lpdev.financemanagerapi.security.DTO.*;
import com.lpdev.financemanagerapi.security.model.entities.User;
import com.lpdev.financemanagerapi.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final WalletRepository walletRepository;
    private final EmailService emailService;

    @Value("${api.frontend.url}")
    private String frontend_url; // dinamic link for front end (dev, prod)

    @Transactional
    public RegisterResponseDTO userRegister(RegisterDTO dto){

        if (userRepository.findByEmail(dto.email()).isPresent()){
            throw new FinanceManagerConflictException("The e-mail " + dto.email() + " already´s registered.");
        }

        String pass = passwordEncoder.encode(dto.password());
        String token = UUID.randomUUID().toString();
        String tokenLink = frontend_url + "/email-verification?token="+token;
        boolean enabled = false;
        String subject = "Your Verification Code - FinanceManager";
        String emailBody = String.format(
                """
                <p>Olá, %s </p>
                <p>Seja Bem-vindo ao Finance Manager!</p>
                <p>Clique no link abaixo para ativar sua conta.</p>
                <a href="%s">Clique para ativar sua conta!</a>
                <p>Se você não criou a conta, ignore esse e-mail.</p>
                """, dto.firstName(), tokenLink);

        emailService.sendEmail(dto.email(), subject, emailBody);

        User user = User.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .username(dto.username())
                .email(dto.email())
                .password(pass)
                .enabled(enabled)
                .verificationCode(token)
                .build();

        userRepository.save(user);

        BigDecimal balance = BigDecimal.ZERO;
        Wallet wallet = new Wallet(null, balance, user);

        walletRepository.save(wallet);

        return new RegisterResponseDTO(user);
    }

    @Transactional
    public LoginResponseDTO userLogin(LoginDTO dto){
        var pass = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = authenticationManager.authenticate(pass);

        User authenticatedUser = (User) auth.getPrincipal();
        String token = tokenService.generateToken(authenticatedUser);

        return new LoginResponseDTO(authenticatedUser, token);
    }

    @Transactional
    public User findUserByAuth(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(()
                -> new FinanceManagerNotFoundException("Not found user authenticated with email: " + email));
    }

    @Transactional
    public void verifyAccount(String token){

        User user = userRepository.findUserByVerificationCode(token).orElseThrow(
                () -> new FinanceManagerNotFoundException("Not found any user with this token"));

        if (user.isEnabled()){
            throw new FinanceManagerBadRequestException("Account already verified");
        }

        user.setEnabled(true);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findUserByVerificationToken(String token){
        return userRepository.findUserByVerificationCode(token).orElseThrow(() ->
                new FinanceManagerNotFoundException("Not found user with token: " + token));
    }


    @Transactional(readOnly = true)
    public boolean tokenVerification(String token){
        User user = findUserByVerificationToken(token);
        return user.getVerificationCode().equals(token) ? true : false;
    }

    @Transactional
    public void enableAccount(String token){
        User user = findUserByVerificationToken(token);
        user.setEnabled(true);
        log.info("User {} enabled successfully", user.getEmail());
    }

    @Transactional
    public void forgotPassword(String email){

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new FinanceManagerNotFoundException("Not found user with email" + email));
        log.info("Encontrado user {} com o email {} -> para recuperação de senha", user.getFirstName(), email);

        String recoveryToken = UUID.randomUUID().toString();
        String link = frontend_url + "/?recoveryToken=" + recoveryToken;
        String subject = "Seu pedido de recuperação de senha - FinanceManager";
        String emailBody = String.format("""
                <p>Olá, %s </p>
                <p>Recebemos seu pedido para recuperar sua senha!</p>
                <p>Clique no link abaixo para alterar sua senha.</p>
                <a href="%s">Clique para ativar sua conta!</a>
                """, user.getFirstName(), link);

        user.setRecoveryToken(recoveryToken);
        user.setRecoveryTokenExpiry(Instant.now().plusSeconds(1800)); // 30 mins

        userRepository.save(user);

        emailService.sendEmail(email, subject, emailBody);
        log.info("Email de recuperação de senha enviado para {}!", user.getFirstName());
    }

    @Transactional
    public void changePassword(ChangePasswordDTO dto){

        User user = userRepository.findUserByRecoveryToken(dto.recoveryToken()).orElseThrow(
                () -> new FinanceManagerNotFoundException("Not found any user with this recovery token"));

        if (user.getRecoveryTokenExpiry().isBefore(Instant.now())){
            throw new FinanceManagerBadRequestException("The recovery token has expired!");
        }

        log.info("Mudando a senha do usuario {}", user.getFirstName());
        user.setPassword(passwordEncoder.encode(dto.newPassword()));

        user.setRecoveryToken(null);
        user.setRecoveryTokenExpiry(null);

        userRepository.save(user);
        log.info("Senha do usuario {} foi alterada com sucesso!", user.getFirstName());
    }

}