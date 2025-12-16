package com.lpdev.financemanagerapi.repositories;

import com.lpdev.financemanagerapi.model.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(nativeQuery = true, value = """
        SELECT t.* FROM tb_transaction t
        JOIN tb_user u ON t.user_id = u.id
        WHERE t.transaction_type = 'WITHDRAW'
        AND u.id = :user_id
""")
    Set<Transaction> myAllExpensesTransactions(@Param("user_id") Long user_id);

}