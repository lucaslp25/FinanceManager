package com.lpdev.financemanagerapi.repositories;

import com.lpdev.financemanagerapi.model.entities.DepositCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositCategoryRepository extends JpaRepository<DepositCategory, Long> {
}
