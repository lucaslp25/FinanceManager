package com.lpdev.financemanagerapi.controllers;

import com.lpdev.financemanagerapi.DTO.*;
import com.lpdev.financemanagerapi.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

    @GetMapping(value = "/withdraw-transactions")
    public ResponseEntity<List<WithdrawTransactionResponseDTO>> findAllWithdrawTransactions(){
        List<WithdrawTransactionResponseDTO> response = transactionService.findAllWithdrawTransactions();
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping(value = "/{id}/edit")
    public ResponseEntity<WithdrawTransactionResponseDTO> editWithdrawTransaction(@PathVariable String id, @RequestBody WithdrawTransactionEditDTO dto){
        WithdrawTransactionResponseDTO response = transactionService.editTransaction(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<Void> deleteWithdrawTransaction(@PathVariable String id){
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
