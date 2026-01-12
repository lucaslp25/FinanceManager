package com.lpdev.financemanagerapi.DTO;

import com.lpdev.financemanagerapi.model.entities.DepositCategory;

public record DepositCategoryResponseDTO(
        Long id,
        String name
) {
    public DepositCategoryResponseDTO(DepositCategory entity){
        this(entity.getId(), entity.getName());
    }
}
