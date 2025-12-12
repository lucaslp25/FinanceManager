package com.lpdev.financemanagerapi.repositories;

import com.lpdev.financemanagerapi.model.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Query(nativeQuery = true, value = """
        SELECT tw.* FROM tb_wallet tw 
        JOIN tb_user u ON tw.user_id = u.id
        WHERE u.email = :email
""")
    Optional<Wallet> findWalletByUserEmail(@Param("email") String email);

}
