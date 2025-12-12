package com.lpdev.financemanagerapi.DTO;

import com.lpdev.financemanagerapi.model.entities.Wallet;

import java.math.BigDecimal;

public record WalletResponseDTO(
        BigDecimal balance,
        Long userId
) {
    public WalletResponseDTO(Wallet entity){
        this(entity.getBalance(), entity.getWalletOwner().getId());
    }
}
