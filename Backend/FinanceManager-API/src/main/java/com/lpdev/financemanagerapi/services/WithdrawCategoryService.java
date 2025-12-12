package com.lpdev.financemanagerapi.services;

import com.lpdev.financemanagerapi.DTO.WithdrawCategoryDTO;
import com.lpdev.financemanagerapi.DTO.WithdrawCategoryResponseDTO;
import com.lpdev.financemanagerapi.model.entities.WithdrawCategory;
import com.lpdev.financemanagerapi.repositories.WithdrawCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WithdrawCategoryService {

    private final WithdrawCategoryRepository withdrawCategoryRepository;

    @Transactional(readOnly = true)
    public Set<WithdrawCategoryResponseDTO> findAll(){
        List<WithdrawCategory> withdrawCategories = withdrawCategoryRepository.findAll();
        return withdrawCategories.stream().
                map(WithdrawCategoryResponseDTO::new)
                .collect(Collectors.toSet());
    }

    @Transactional
    public WithdrawCategoryResponseDTO create(WithdrawCategoryDTO dto){
        WithdrawCategory newCategory = new WithdrawCategory();
        newCategory.setName(dto.name().toUpperCase());
        withdrawCategoryRepository.save(newCategory);
        return new WithdrawCategoryResponseDTO(newCategory);
    }

    @Transactional(readOnly = true)
    protected WithdrawCategory findById(Long id){
        return withdrawCategoryRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No WithdrawCategory found with id: " + id));
    }
}
