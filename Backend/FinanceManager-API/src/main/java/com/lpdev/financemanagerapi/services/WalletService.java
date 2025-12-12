package com.lpdev.financemanagerapi.services;

import com.lpdev.financemanagerapi.DTO.BalanceDTO;
import com.lpdev.financemanagerapi.DTO.WalletResponseDTO;
import com.lpdev.financemanagerapi.DTO.WithdrawDTO;
import com.lpdev.financemanagerapi.exceptions.FinanceManagerNotFoundException;
import com.lpdev.financemanagerapi.model.entities.Wallet;
import com.lpdev.financemanagerapi.model.enums.TransactionType;
import com.lpdev.financemanagerapi.repositories.WalletRepository;
import com.lpdev.financemanagerapi.security.model.entities.User;
import com.lpdev.financemanagerapi.security.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public WalletResponseDTO findWalletByUserAuth(){
        Wallet wallet = findWallet();
        return new WalletResponseDTO(wallet);
    }

    private Wallet findWallet(){
        User user = userService.findUserByAuth();
        return walletRepository.findWalletByUserEmail(user.getEmail()).orElseThrow(()
                -> new FinanceManagerNotFoundException("Not found wallet of user with email: " + user.getEmail()));
    }

    //internal use method
    protected  Wallet updateBalance(BigDecimal amount, TransactionType type){
        Wallet wallet = findWallet();
        log.info("Found wallet: {} ", wallet);

        if (type == TransactionType.WITHDRAWAL){
            wallet.decreaseBalance(amount);
        }else{
            wallet.addBalance(amount);
        }

        String logMessage = TransactionType.WITHDRAWAL == type ? "The wallet withdraw has been successfully" : "The wallet deposit has been successfully";

        log.info(logMessage);

        walletRepository.save(wallet);
        return wallet;
    }

}
