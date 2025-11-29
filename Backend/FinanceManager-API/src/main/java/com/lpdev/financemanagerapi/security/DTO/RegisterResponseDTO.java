package com.lpdev.financemanagerapi.security.DTO;

import com.lpdev.financemanagerapi.security.model.entities.User;
import com.lpdev.financemanagerapi.security.model.enums.UserRole;
import lombok.Builder;

@Builder
public record RegisterResponseDTO(

        String firstName,
        String lastName,
        String email,
        UserRole role
) {
    public RegisterResponseDTO(User entity){
        this(
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getRole()
        );

    }
}
