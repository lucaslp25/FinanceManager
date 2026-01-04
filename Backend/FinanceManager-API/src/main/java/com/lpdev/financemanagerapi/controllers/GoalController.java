package com.lpdev.financemanagerapi.controllers;

import com.lpdev.financemanagerapi.DTO.GoalCreateDTO;
import com.lpdev.financemanagerapi.DTO.GoalResponseDTO;
import com.lpdev.financemanagerapi.DTO.GoalUpdateDTO;
import com.lpdev.financemanagerapi.services.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/goals")
public class GoalController {

    private final GoalService service;

    @PostMapping(value = "/create")
    public ResponseEntity<GoalResponseDTO> insertGoal(@RequestBody GoalCreateDTO dto){

        GoalResponseDTO response = service.addNewGoal(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<List<GoalResponseDTO>> getAllGoals(){
        List<GoalResponseDTO> response = service.findAllGoalsByUser();
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping(value = "/{id}/update")
    public ResponseEntity<GoalResponseDTO> updateGoal(@PathVariable Long id, @RequestBody GoalUpdateDTO dto){
        GoalResponseDTO response = service.editGoal(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long id){
        this.service.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }

}