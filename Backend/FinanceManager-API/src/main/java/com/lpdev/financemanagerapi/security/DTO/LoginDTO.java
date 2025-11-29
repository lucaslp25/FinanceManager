package com.lpdev.financemanagerapi.security.DTO;

import com.lpdev.financemanagerapi.security.model.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(

        @Email(message = "Verify the e-mail format")
        @NotBlank(message = "The e-mail cannot be null.")
        String email,

        @NotBlank(message = "The password cannot be null.")
        String password
) {
    public LoginDTO(User entity){
        this(entity.getEmail(), entity.getPassword());
    }
}
