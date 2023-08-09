package com.example.taskmanager.controller;


import com.example.taskmanager.dto.goal.GoalCreationDto;
import com.example.taskmanager.dto.goal.GoalDto;
import com.example.taskmanager.dto.goal.GoalUpdateDto;
import com.example.taskmanager.entity.Goal;
import com.example.taskmanager.mapper.GoalMapper;
import com.example.taskmanager.service.GoalService;
import com.example.taskmanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myTask")
public class GoalController {


    private final GoalService goalService;

    private final UserService userService;

    private final GoalMapper goalMapper;


    @GetMapping("/goals")
    public ResponseEntity<List<GoalDto>> getAllGoals(Principal principal){
        var goals = goalService.getAll(userService.findByUsername(principal.getName()).get().getId())
                .stream().map(goalMapper::toDto)
                .toList();
        return new ResponseEntity<>(goals, HttpStatus.OK);
    }


    @PostMapping("/createGoal")
    public ResponseEntity<GoalDto> createGoal(Principal principal,@Valid @RequestBody GoalCreationDto goalCreationDto){

        var createdGoal = goalService.createOne(userService.findByUsername(principal.getName()).get(), goalMapper.toEntity(goalCreationDto));
        return new ResponseEntity<>(goalMapper.toDto(createdGoal), HttpStatus.OK);
    }

    @PatchMapping("/goals/{id}")
    public ResponseEntity<GoalDto> updateGoal(@PathVariable Long id,@Valid @RequestBody GoalUpdateDto goalUpdateDto, Principal principal){
        return ResponseEntity.of(goalService.findById(id)
                .map(goal -> goalMapper.updateGoal(goalUpdateDto, goal))
                .map(updatedGoal -> goalService.updateById(id,userService.findByUsername(principal.getName()).get(),updatedGoal))
                .map(goalMapper::toDto));
    }

    @DeleteMapping("/goals/{id}")
    public ResponseEntity<Void> deleteGoalById(@PathVariable Long id, Principal principal){
        goalService.deleteById(id, userService.findByUsername(principal.getName()).get());
        return ResponseEntity.noContent().build();
    }





}
