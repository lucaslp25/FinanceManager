package com.lpdev.financemanagerapi.repositories;

import com.lpdev.financemanagerapi.model.entities.WithdrawCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawCategoryRepository extends JpaRepository<WithdrawCategory, Long> {
}
