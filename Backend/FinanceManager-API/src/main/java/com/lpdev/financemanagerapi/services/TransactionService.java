package com.lpdev.financemanagerapi.services;

import com.lpdev.financemanagerapi.DTO.*;
import com.lpdev.financemanagerapi.exceptions.FinanceManagerBadRequestException;
import com.lpdev.financemanagerapi.exceptions.FinanceManagerNotFoundException;
import com.lpdev.financemanagerapi.model.entities.Transaction;
import com.lpdev.financemanagerapi.model.entities.Wallet;
import com.lpdev.financemanagerapi.model.entities.WithdrawCategory;
import com.lpdev.financemanagerapi.model.enums.TransactionType;
import com.lpdev.financemanagerapi.repositories.TransactionRepository;
import com.lpdev.financemanagerapi.security.model.entities.User;
import com.lpdev.financemanagerapi.security.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final WalletService walletService;
    private final WithdrawCategoryService withdrawCategoryService;

    @Transactional
    public TransactionResponseDTO depositBalance(BalanceDTO dto){

        if (dto.amount().equals(BigDecimal.ZERO) || dto.amount().compareTo(BigDecimal.ZERO) < 0){
            throw new FinanceManagerBadRequestException("The amount must be greater than zero!");
        }

        User user = userService.findUserByAuth();
        log.info("Found user with id: {} to make the transaction...", user.getId());
        Transaction transaction = new Transaction();

        transaction.setDescription("Deposit in value of " + dto.amount() + " to " + user.getEmail() + " account.");
        transaction.setAmount(dto.amount());
        transaction.setTransactiontype(TransactionType.DEPOSIT);
        transaction.setUser(user);

        log.info("Transaction successfully deposited! value of {} to  {} account", dto.amount(), user.getEmail());

        // update the wallet value.
        Wallet wallet = walletService.updateBalance(dto.amount(), TransactionType.DEPOSIT);

        transactionRepository.save(transaction);

        user.addTransaction(transaction);

        return new TransactionResponseDTO(transaction, wallet);
    }

    @Transactional
    public TransactionResponseDTO withdrawBalance(WithdrawDTO dto){

        if (dto.amount().equals(BigDecimal.ZERO) || dto.amount().compareTo(BigDecimal.ZERO) < 0){
            throw new FinanceManagerBadRequestException("The amount must be greater than zero!");
        }

        WithdrawCategory withdrawCategory = new WithdrawCategory();

        if (dto.categoryId() == null){
            throw new FinanceManagerBadRequestException("The category id must not be null!");
        }else{
             withdrawCategory = withdrawCategoryService.findById(dto.categoryId());
        }

        User user = userService.findUserByAuth();

        log.info("Found user with id: {} to make the withdraw.", user.getId());

        String description = dto.description() != null ? dto.description() :
                "Withdraw value of " + dto.amount() + " to " + user.getEmail() + " account.";

        Transaction transaction = new Transaction();
        transaction.setAmount(dto.amount());
        transaction.setTransactiontype(TransactionType.WITHDRAW);
        transaction.setWithdrawCategory(withdrawCategory);
        transaction.setDescription(description);
        transaction.setUser(user);
        transaction.setDate(dto.date());

        Wallet wallet = walletService.updateBalance(dto.amount(), TransactionType.WITHDRAW);

        transactionRepository.save(transaction);

        return new TransactionResponseDTO(transaction, wallet);
    }

    @Transactional(readOnly = true)
    public List<WithdrawTransactionResponseDTO> findAllWithdrawTransactions(){
        User user = userService.findUserByAuth();
        List<Transaction> transactions = transactionRepository.myAllExpensesTransactions(user.getId());
        return transactions.stream().map(WithdrawTransactionResponseDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public WithdrawTransactionResponseDTO editTransaction(String id, WithdrawTransactionEditDTO dto){
        Transaction transaction = this.transactionRepository.findById(id).orElseThrow(() -> new FinanceManagerNotFoundException("Cannot find transaction with id: " + id));

        BigDecimal oldBalance = transaction.getAmount();
        copyEditData(dto, transaction);
        BigDecimal newBalance = transaction.getAmount();

        if (oldBalance.compareTo(newBalance) != 0){

            if(newBalance.compareTo(oldBalance) > 0){
                BigDecimal diff = newBalance.subtract(oldBalance);
                this.walletService.updateBalance(diff, TransactionType.WITHDRAW);
            }else{
                BigDecimal diff = oldBalance.subtract(newBalance);
                this.walletService.updateBalance(diff, TransactionType.DEPOSIT);
            }
        }
        return new WithdrawTransactionResponseDTO(transaction);
    }

    @Transactional
    public void deleteTransaction(String id){

        if(!transactionRepository.existsById(id)){
            throw new FinanceManagerNotFoundException("Cannot find transaction with id: " + id);
        }
        this.transactionRepository.deleteById(id);
    }

    @Transactional
    protected Transaction copyEditData(WithdrawTransactionEditDTO dto, Transaction transaction){
        // cleaning data
        BigDecimal amount = dto.amount() != null ? dto.amount() : transaction.getAmount();
        String description = dto.description() != null ? dto.description() : transaction.getDescription();
        Instant date = dto.date() != null ? dto.date() : transaction.getDate();

        WithdrawCategory category = dto.categoryId() != null ? withdrawCategoryService.findById(dto.categoryId()) : transaction.getWithdrawCategory();

        transaction.setDescription(description);
        transaction.setAmount(amount);
        transaction.setDate(date);
        transaction.setWithdrawCategory(category);

        return transaction;
    }

}

