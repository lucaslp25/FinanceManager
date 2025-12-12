package com.lpdev.financemanagerapi.controllers;

import com.lpdev.financemanagerapi.DTO.WalletResponseDTO;
import com.lpdev.financemanagerapi.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping(value = "/my-wallet")
    public ResponseEntity<WalletResponseDTO> findWalletByUserauthenticated(){
        WalletResponseDTO dto = walletService.findWalletByUserAuth();
        return ResponseEntity.ok().body(dto);
    }
}
