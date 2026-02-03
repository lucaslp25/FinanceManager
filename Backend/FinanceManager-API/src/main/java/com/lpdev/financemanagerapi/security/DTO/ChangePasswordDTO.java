package com.lpdev.financemanagerapi.security.DTO;

public record ChangePasswordDTO(

        String recoveryToken,
        String newPassword

) {
}

