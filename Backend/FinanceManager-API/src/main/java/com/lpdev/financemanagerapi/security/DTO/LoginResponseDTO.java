package com.lpdev.financemanagerapi.security.DTO;

import com.lpdev.financemanagerapi.security.model.entities.User;
import com.lpdev.financemanagerapi.security.model.enums.UserRole;
import lombok.Builder;

@Builder
public record LoginResponseDTO(
        String firstName,
        String lastName,
        String email,
        UserRole role,
        String token
) {
    public LoginResponseDTO(User entity, String token){
        this(
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getRole(),
                token
        );
    }
}
