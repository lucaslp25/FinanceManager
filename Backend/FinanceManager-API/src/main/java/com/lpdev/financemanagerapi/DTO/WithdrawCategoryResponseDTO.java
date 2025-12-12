package com.lpdev.financemanagerapi.DTO;

import com.lpdev.financemanagerapi.model.entities.WithdrawCategory;

public record WithdrawCategoryResponseDTO(
        Long id,
        String name
) {
    public WithdrawCategoryResponseDTO(WithdrawCategory entity){
        this(entity.getId(), entity.getName());
    }
}
