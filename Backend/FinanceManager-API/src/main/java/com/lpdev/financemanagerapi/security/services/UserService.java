package com.lpdev.financemanagerapi.security.services;

import com.lpdev.financemanagerapi.exceptions.FinanceManagerBadRequestException;
import com.lpdev.financemanagerapi.exceptions.FinanceManagerConflictException;
import com.lpdev.financemanagerapi.exceptions.FinanceManagerNotFoundException;
import com.lpdev.financemanagerapi.microservices.email.EmailService;
import com.lpdev.financemanagerapi.model.entities.Transaction;
import com.lpdev.financemanagerapi.model.entities.Wallet;
import com.lpdev.financemanagerapi.repositories.WalletRepository;
import com.lpdev.financemanagerapi.security.DTO.LoginDTO;
import com.lpdev.financemanagerapi.security.DTO.LoginResponseDTO;
import com.lpdev.financemanagerapi.security.DTO.RegisterDTO;
import com.lpdev.financemanagerapi.security.DTO.RegisterResponseDTO;
import com.lpdev.financemanagerapi.security.model.entities.User;
import com.lpdev.financemanagerapi.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final WalletRepository walletRepository;
    private final EmailService emailService;

    @Value("${api.frontend.url}r")
    private String frontend_url; // dinamic link for front end (dev, prod)

    @Transactional
    public RegisterResponseDTO userRegister(RegisterDTO dto){

        // depois adicionar validações aqui mandando erro do tipo bad request e mandando pro global handler para ver se a mensagem vai para o postman.

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
                Olá, %s Seja Bem-vindo ao Finance Manager!
                
                
                Clique no link abaixo para ativar sua conta.
                
                
                %s
                
                
                Se você não criou a conta, ignore esse e-mail.
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

    public void addTransaction (Transaction transaction){
        User user = findUserByAuth();
        user.addTransaction(transaction);
        userRepository.save(user);
    }

    @Transactional
    public void verifyAccount(String token){

        User user = userRepository.findUserByRecoveryToken(token).orElseThrow(
                () -> new FinanceManagerNotFoundException("Not found any user with this token"));

        if (user.isEnabled()){
            throw new FinanceManagerBadRequestException("Account already verified");
        }

        user.setEnabled(true);

        userRepository.save(user);
    }



}
