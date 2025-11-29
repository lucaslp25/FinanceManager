package com.lpdev.financemanagerapi.security.services;

import com.lpdev.financemanagerapi.exceptions.FinanceManagerConflictException;
import com.lpdev.financemanagerapi.security.DTO.LoginDTO;
import com.lpdev.financemanagerapi.security.DTO.LoginResponseDTO;
import com.lpdev.financemanagerapi.security.DTO.RegisterDTO;
import com.lpdev.financemanagerapi.security.DTO.RegisterResponseDTO;
import com.lpdev.financemanagerapi.security.model.entities.User;
import com.lpdev.financemanagerapi.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Transactional
    public RegisterResponseDTO userRegister(RegisterDTO dto){

        // depois adicionar validações aqui mandando erro do tipo bad request e mandando pro global handler para ver se a mensagem vai para o postman.

        if (userRepository.findByEmail(dto.email()).isPresent()){
            throw new FinanceManagerConflictException("The e-mail " + dto.email() + " already´s registered.");
        }

        String pass = passwordEncoder.encode(dto.password());

        User user = User.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .password(pass)
                .build();

        userRepository.save(user);

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

}
