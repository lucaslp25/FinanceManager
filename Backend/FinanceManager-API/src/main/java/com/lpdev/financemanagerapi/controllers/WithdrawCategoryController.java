package com.lpdev.financemanagerapi.controllers;

import com.lpdev.financemanagerapi.DTO.WithdrawCategoryDTO;
import com.lpdev.financemanagerapi.DTO.WithdrawCategoryResponseDTO;
import com.lpdev.financemanagerapi.services.WithdrawCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/withdrawcategory")
@RequiredArgsConstructor
public class WithdrawCategoryController {

    private final WithdrawCategoryService service;

    @GetMapping
    public ResponseEntity<Set<WithdrawCategoryResponseDTO>> findAllCategories(){
        Set<WithdrawCategoryResponseDTO> response = service.findAll();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<WithdrawCategoryResponseDTO> createCategory(@RequestBody WithdrawCategoryDTO dto){
        WithdrawCategoryResponseDTO response = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }
}
