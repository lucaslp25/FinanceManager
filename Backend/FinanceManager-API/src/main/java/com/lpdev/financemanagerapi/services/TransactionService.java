package com.lpdev.financemanagerapi.services;

import com.lpdev.financemanagerapi.DTO.*;
import com.lpdev.financemanagerapi.exceptions.FinanceManagerBadRequestException;
import com.lpdev.financemanagerapi.exceptions.FinanceManagerNotFoundException;
import com.lpdev.financemanagerapi.model.entities.DepositCategory;
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
    private final DepositCategoryService depositCategoryService;

    @Transactional
    public TransactionResponseDTO depositBalance(BalanceDTO dto){

        if (dto.amount().equals(BigDecimal.ZERO) || dto.amount().compareTo(BigDecimal.ZERO) < 0){
            throw new FinanceManagerBadRequestException("The amount must be greater than zero!");
        }

        DepositCategory depositCategory;

        if (dto.categoryId() == null){
            throw new FinanceManagerBadRequestException("The category id must not be null!");
        }else{
            depositCategory = depositCategoryService.findById(dto.categoryId());
        }

        User user = userService.findUserByAuth();
        log.info("Found user with id: {} to make the transaction...", user.getId());
        Transaction transaction = new Transaction();

        transaction.setDescription("Deposit in value of " + dto.amount() + " to " + user.getEmail() + " account.");
        transaction.setAmount(dto.amount());
        transaction.setTransactiontype(TransactionType.DEPOSIT);
        transaction.setDepositCategory(depositCategory);
        transaction.setDate(dto.date());
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

        WithdrawCategory withdrawCategory;

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

    @Transactional
    public TransactionResponseDTO editTransaction(String id, TransactionEditDTO dto){

        Transaction transaction = this.transactionRepository.findById(id).orElseThrow(()
                -> new FinanceManagerNotFoundException("Cannot find transaction with id: " + id));

        Wallet wallet = walletService.findWallet();

        updateWalletBalance(transaction, dto);
        copyEditData(dto, transaction);

        return new TransactionResponseDTO(transaction, wallet);
    }

    @Transactional
    protected void updateWalletBalance(Transaction transaction, TransactionEditDTO dto){

        BigDecimal oldBalance = transaction.getAmount();
        BigDecimal newBalance = dto.amount();

        TransactionType type = transaction.getTransactiontype();

        if (oldBalance.compareTo(newBalance) != 0) {
            if (type.equals(TransactionType.WITHDRAW)){

                if(newBalance.compareTo(oldBalance) > 0){
                    BigDecimal diff = newBalance.subtract(oldBalance);
                    this.walletService.updateBalance(diff, TransactionType.WITHDRAW);
                }else{
                    BigDecimal diff = oldBalance.subtract(newBalance);
                    this.walletService.updateBalance(diff, TransactionType.DEPOSIT);
                }

            } else {

                if(newBalance.compareTo(oldBalance) > 0){
                    BigDecimal diff = newBalance.subtract(oldBalance);
                    this.walletService.updateBalance(diff, TransactionType.DEPOSIT);
                } else {
                    BigDecimal diff = oldBalance.subtract(newBalance);
                    this.walletService.updateBalance(diff, TransactionType.WITHDRAW);
                }
            }
        }
    }

    @Transactional
    public void deleteTransaction(String id){
        if(!transactionRepository.existsById(id)){
            throw new FinanceManagerNotFoundException("Cannot find transaction with id: " + id);
        }
        this.transactionRepository.deleteById(id);
    }

    @Transactional
    protected Transaction copyEditData(TransactionEditDTO dto, Transaction transaction){
        // cleaning data
        BigDecimal amount = dto.amount() != null ? dto.amount() : transaction.getAmount();
        String description = dto.description() != null ? dto.description() : transaction.getDescription();
        Instant date = dto.date() != null ? dto.date() : transaction.getDate();
        defineCategory(dto.categoryId(), transaction);
        transaction.setDescription(description);
        transaction.setAmount(amount);
        transaction.setDate(date);

        return transaction;
    }

    @Transactional
    protected void defineCategory(Long id, Transaction transaction){
        if (id != null){

            if (transaction.getTransactiontype() == TransactionType.WITHDRAW){
                WithdrawCategory withdrawCategory = withdrawCategoryService.findById(id);
                transaction.setWithdrawCategory(withdrawCategory);

            } else {
                DepositCategory depositCategory = depositCategoryService.findById(id);
                System.out.println("Categoria que sera alterada: "+ depositCategory);
                transaction.setDepositCategory(depositCategory);
            }

        } else {

            if (transaction.getTransactiontype() == TransactionType.WITHDRAW){
                transaction.setWithdrawCategory(transaction.getWithdrawCategory());

            } else {
                transaction.setDepositCategory(transaction.getDepositCategory());
            }

        }
    }

    @Transactional(readOnly = true)
    public List<TransactionResponseDTO> findAllWithdrawTransactions(){
        return auxFindAll("WITHDRAW");
    }

    @Transactional(readOnly = true)
    public List<TransactionResponseDTO> findAllDepositTransactions(){
        return auxFindAll("DEPOSIT");
    }

    @Transactional(readOnly = true)
    public List<TransactionResponseDTO> findAllExtractsByUser(){
        return auxFindAll("EXTRACT");
    }

    protected List<TransactionResponseDTO> auxFindAll(String tipe){
        User user = userService.findUserByAuth();
        Wallet wallet = walletService.findWallet();

        List<Transaction> transactions;

        if (tipe.equals("WITHDRAW")){
            transactions = transactionRepository.myAllExpensesTransactions(user.getId());
        } else if (tipe.equals("DEPOSIT")) {
            transactions = transactionRepository.myAllDepositTransactions(user.getId());
        } else{
            transactions = transactionRepository.findAllTransactionsByUserId(user.getId());
        }

        transactions.stream()

//        return transactions.stream().map(obj -> new TransactionResponseDTO(obj, wallet)).collect(Collectors.toList());
    }

}
