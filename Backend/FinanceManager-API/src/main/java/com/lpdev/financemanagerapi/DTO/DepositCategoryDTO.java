package com.lpdev.financemanagerapi.DTO;

import com.lpdev.financemanagerapi.model.entities.DepositCategory;
import jakarta.validation.constraints.NotBlank;

public record DepositCategoryDTO(
        @NotBlank(message = "The field name cannot be null.")
        String name
) {
    public DepositCategoryDTO(DepositCategory entity){
        this(entity.getName());
    }
}
