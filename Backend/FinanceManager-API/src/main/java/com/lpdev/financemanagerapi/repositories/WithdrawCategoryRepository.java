package com.lpdev.financemanagerapi.repositories;

import com.lpdev.financemanagerapi.model.entities.WithdrawCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithdrawCategoryRepository extends JpaRepository<WithdrawCategory, Long> {


    @Query(nativeQuery = true, value = """
        SELECT * FROM tb_withdraw_category 
        WHERE user_id = :user_id
    """)
    List<WithdrawCategory> findAllWithdrawCategoriesByUser(@Param("user_id") Long user_id);
}
