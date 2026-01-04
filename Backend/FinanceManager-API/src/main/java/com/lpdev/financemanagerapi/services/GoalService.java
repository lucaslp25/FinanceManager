package com.lpdev.financemanagerapi.services;

import com.lpdev.financemanagerapi.DTO.GoalCreateDTO;
import com.lpdev.financemanagerapi.DTO.GoalResponseDTO;
import com.lpdev.financemanagerapi.DTO.GoalUpdateDTO;
import com.lpdev.financemanagerapi.exceptions.FinanceManagerNotFoundException;
import com.lpdev.financemanagerapi.model.entities.Goal;
import com.lpdev.financemanagerapi.repositories.GoalRepository;
import com.lpdev.financemanagerapi.security.model.entities.User;
import com.lpdev.financemanagerapi.security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository repository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<GoalResponseDTO> findAllGoalsByUser(){
        User user = userService.findUserByAuth();
        List<Goal> goals = repository.findAllGoalsByUserId(user.getId());
        return goals.stream().map(GoalResponseDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public GoalResponseDTO addNewGoal(GoalCreateDTO dto){
        Goal goal = new Goal();
        copyData(dto, goal);
        repository.save(goal);
        return new GoalResponseDTO(goal);
    }

    @Transactional
    public GoalResponseDTO editGoal(Long id, GoalUpdateDTO dto){

        Goal goal = this.repository.findById(id).orElseThrow(
                () -> new FinanceManagerNotFoundException("Not found goal with id: " + id));

        goalEditData(goal, dto);
        return new GoalResponseDTO(goal);
    }

    @Transactional
    protected void goalEditData(Goal goal, GoalUpdateDTO dto){

        String name = dto.name() != null ? dto.name() : goal.getName();
        String description = dto.description() != null ? dto.description() : goal.getDescription();
        BigDecimal targetAmount = dto.targetAmount() != null ? dto.targetAmount() : goal.getTargetAmount();
        Instant deadline = dto.deadline() != null ? dto.deadline() : goal.getDeadline();

        goal.setName(name);
        goal.setDescription(description);
        goal.setTargetAmount(targetAmount);
        goal.setDeadline(deadline);
    }

    @Transactional
    public void deleteGoal(Long id){
        if (!repository.existsById(id)){
            throw new FinanceManagerNotFoundException("Not found goal with id: " + id);
        }
        repository.deleteById(id);
    }

    private void copyData(GoalCreateDTO dto, Goal goal) {

        goal.setName(dto.name());
        String description = dto.description() != null ? dto.description() : "Meta sem descrição.";
        goal.setDescription(description);
        goal.setTargetAmount(dto.targetAmount());

        if (dto.deadline() != null){
            goal.setDeadline(dto.deadline());
        }

        goal.setUser(userService.findUserByAuth());

        goal.setInitDate(Instant.now());
    }

}
