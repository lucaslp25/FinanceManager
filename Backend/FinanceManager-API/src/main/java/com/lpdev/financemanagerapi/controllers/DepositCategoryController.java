package com.lpdev.financemanagerapi.controllers;

import com.lpdev.financemanagerapi.DTO.DepositCategoryDTO;
import com.lpdev.financemanagerapi.DTO.DepositCategoryResponseDTO;
import com.lpdev.financemanagerapi.services.DepositCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/deposit-category")
@RequiredArgsConstructor
public class DepositCategoryController {

    private final DepositCategoryService service;

    @GetMapping
    public ResponseEntity<Set<DepositCategoryResponseDTO>> findAllCategories(){
        Set<DepositCategoryResponseDTO> response = service.findAll();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<DepositCategoryResponseDTO> createCategory(@RequestBody DepositCategoryDTO dto){
        DepositCategoryResponseDTO response = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping(value = "/{id}/update")
    public ResponseEntity<DepositCategoryResponseDTO> updateCategory(@RequestBody DepositCategoryDTO dto, @PathVariable Long id){
        DepositCategoryResponseDTO response = service.updateCategory(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
