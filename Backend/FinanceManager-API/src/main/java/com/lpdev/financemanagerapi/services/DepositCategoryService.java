package com.lpdev.financemanagerapi.services;

import com.lpdev.financemanagerapi.DTO.DepositCategoryDTO;
import com.lpdev.financemanagerapi.DTO.DepositCategoryResponseDTO;
import com.lpdev.financemanagerapi.exceptions.FinanceManagerBadRequestException;
import com.lpdev.financemanagerapi.model.entities.DepositCategory;
import com.lpdev.financemanagerapi.repositories.DepositCategoryRepository;
import com.lpdev.financemanagerapi.security.model.entities.User;
import com.lpdev.financemanagerapi.security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepositCategoryService {

    private final DepositCategoryRepository depositCategoryRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public Set<DepositCategoryResponseDTO> findAll(){
        User user = userService.findUserByAuth();
        List<DepositCategory> depositCategories =
                depositCategoryRepository.findAllDepositCategoriesByUser(user.getId());
        return depositCategories.stream().
                map(DepositCategoryResponseDTO::new)
                .collect(Collectors.toSet());
    }

    @Transactional
    public DepositCategoryResponseDTO create(DepositCategoryDTO dto){
        DepositCategory newCategory = new DepositCategory();
        newCategory.setName(dto.name().toUpperCase());
        newCategory.setUser(userService.findUserByAuth());
        depositCategoryRepository.save(newCategory);
        return new DepositCategoryResponseDTO(newCategory);
    }

    @Transactional
    public DepositCategoryResponseDTO updateCategory(Long id, DepositCategoryDTO dto){
        DepositCategory category = findById(id);
        category.setName(dto.name().toUpperCase());
        depositCategoryRepository.save(category);
        return new DepositCategoryResponseDTO(category);
    }

    @Transactional
    public void deleteCategory(Long id){
        if (!depositCategoryRepository.existsById(id)){
            throw new FinanceManagerBadRequestException("Cannot find category with id: " + id);
        }
        depositCategoryRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    protected DepositCategory findById(Long id){
        DepositCategory cat = depositCategoryRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No Deposit Category found with id: " + id));

        System.out.print("Categoria achada: " + cat);
        return cat;
    }
}
