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
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService service){
        this.transactionService = service;
    }

    @PostMapping(value = "/deposit")
    public ResponseEntity<TransactionResponseDTO> deposit(@RequestBody BalanceDTO dto) {
        TransactionResponseDTO response = transactionService.depositBalance(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.transactionId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping(value = "/withdraw")
    public ResponseEntity<TransactionResponseDTO> deposit(@RequestBody WithdrawDTO dto) {
        TransactionResponseDTO response = transactionService.withdrawBalance(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.transactionId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping(value = "/withdraw-transactions")
    public ResponseEntity<List<TransactionResponseDTO>> findAllWithdrawTransactions(){
        List<TransactionResponseDTO> response = transactionService.findAllWithdrawTransactions();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/deposit-transactions")
    public ResponseEntity<List<TransactionResponseDTO>> findAllDepositTransactions(){
        List<TransactionResponseDTO> response = transactionService.findAllDepositTransactions();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping()
    public ResponseEntity<List<TransactionResponseDTO>> findAllTransactions(){
        List<TransactionResponseDTO> response = transactionService.findAllExtractsByUser();
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping(value = "/{id}/edit")
    public ResponseEntity<TransactionResponseDTO> editWithdrawTransaction(@PathVariable String id, @RequestBody TransactionEditDTO dto){
        TransactionResponseDTO response = transactionService.editTransaction(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<Void> deleteWithdrawTransaction(@PathVariable String id){
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
