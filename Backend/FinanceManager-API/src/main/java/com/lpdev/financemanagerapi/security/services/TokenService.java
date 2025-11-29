package com.lpdev.financemanagerapi.security.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.lpdev.financemanagerapi.exceptions.FinanceManagerSecurityException;
import com.lpdev.financemanagerapi.security.model.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${spring-security-secret-key}")
    private String secretValue;

    private final String ISSUER = "FinanceManager-API";

    public String generateToken(User user){

        Algorithm algorithm = Algorithm.HMAC256(secretValue);

        try{
            String token = JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getEmail())
                    .withExpiresAt(expiresTime())
                    .sign(algorithm);
            return token;

        }catch (JWTCreationException ex){
            throw new FinanceManagerSecurityException("Error in verify the security token: " + ex.getMessage());
        }
    }

    public String verifyToken(String token){

        Algorithm algorithm = Algorithm.HMAC256(secretValue);
        try{
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException ex){
            throw new FinanceManagerSecurityException("Error in verify the security token: " + ex.getMessage());
        }
    }

    private Instant expiresTime() {
        return LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-03:00"));
    }

}
