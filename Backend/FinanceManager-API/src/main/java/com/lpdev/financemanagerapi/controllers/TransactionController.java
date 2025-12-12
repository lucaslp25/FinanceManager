package com.lpdev.financemanagerapi.controllers;

import com.lpdev.financemanagerapi.DTO.BalanceDTO;
import com.lpdev.financemanagerapi.DTO.TransactionResponseDTO;
import com.lpdev.financemanagerapi.DTO.WithdrawDTO;
import com.lpdev.financemanagerapi.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping(value = "/deposit")
    public ResponseEntity<TransactionResponseDTO> deposit(@RequestBody BalanceDTO dto) {
        TransactionResponseDTO response = transactionService.depositBalance(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping(value = "/withdraw")
    public ResponseEntity<TransactionResponseDTO> deposit(@RequestBody WithdrawDTO dto) {
        TransactionResponseDTO response = transactionService.withdrawBalance(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }
}
