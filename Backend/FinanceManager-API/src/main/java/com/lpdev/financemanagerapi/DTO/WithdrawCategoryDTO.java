package com.lpdev.financemanagerapi.DTO;

import com.lpdev.financemanagerapi.model.entities.WithdrawCategory;
import jakarta.validation.constraints.NotBlank;

public record WithdrawCategoryDTO(
        @NotBlank(message = "The field name cannot be null.")
        String name
) {
    public WithdrawCategoryDTO(WithdrawCategory entity){
        this(entity.getName());
    }
}
