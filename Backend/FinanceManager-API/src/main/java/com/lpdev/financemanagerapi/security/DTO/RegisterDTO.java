package com.lpdev.financemanagerapi.security.DTO;

import com.lpdev.financemanagerapi.security.model.entities.User;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record RegisterDTO(

        @Length(max = 25)
        @NotBlank(message = "The field firsName cannot be null.")
        String firstName,

        @Length(max = 25)
        @NotBlank(message = "The field lastName cannot be null.")
        String lastName,

        @Length(max = 60)
        @Nullable
        String username,

        @Length(max = 100)
        @Email(message = "Verify the e-mail format")
        @NotBlank(message = "The field e-mail cannot be null.")
        String email,

        @NotBlank(message = "The field password cannot be null.")
        String password
) {
    public RegisterDTO(User entitiy){
        this(
                entitiy.getFirstName(),
                entitiy.getLastName(),
                entitiy.getUsername(),
                entitiy.getEmail(),
                entitiy.getPassword()
        );
    }
}
