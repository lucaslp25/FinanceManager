package com.lpdev.financemanagerapi.repositories;

import com.lpdev.financemanagerapi.model.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
