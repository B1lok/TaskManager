package com.example.taskmanager.controller;


import com.example.taskmanager.dto.everydayTask.EverydayTaskCreationDto;
import com.example.taskmanager.dto.everydayTask.EverydayTaskDto;
import com.example.taskmanager.dto.everydayTask.EverydayTaskUpdateDto;
import com.example.taskmanager.entity.EverydayTask;
import com.example.taskmanager.mapper.EverydayTaskMapper;
import com.example.taskmanager.service.EverydayTaskService;
import com.example.taskmanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myTask")
public class EverydayTaskController {



    private final EverydayTaskService everydayTaskService;


    private final UserService userService;

    private final EverydayTaskMapper everydayTaskMapper;

    @GetMapping("/everydayTasks/{date}")
    public ResponseEntity<List<EverydayTaskDto>> getEverydayTasks(Principal principal, @PathVariable LocalDate date){
        return everydayTaskService.getAllByDate(userService.findByUsername(principal.getName()).get().getId(), date).stream()
                .map(everydayTaskMapper::toDto)
                .collect(Collectors.collectingAndThen(Collectors.toList(), ResponseEntity::ok));
    }


    @PostMapping("/createEverydayTask")
    public ResponseEntity<EverydayTaskDto> createTask(Principal principal,@Valid @RequestBody EverydayTaskCreationDto everydayTaskCreationDto){
        var createdTask = everydayTaskService.createTask(everydayTaskMapper.toEntity(everydayTaskCreationDto), userService.findByUsername(principal.getName()).get());
        return new ResponseEntity<>(everydayTaskMapper.toDto(createdTask), HttpStatus.OK);
    }


    @PatchMapping("/updateEverydayTask/{id}")
    public ResponseEntity<EverydayTaskDto> updateTask(Principal principal, @PathVariable Long id, @Valid @RequestBody EverydayTaskUpdateDto updateDto){
        return ResponseEntity.of(everydayTaskService.findById(id)
                .map(task -> everydayTaskService.updateTask(task,updateDto,userService.findByUsername(principal.getName()).get(), id))
                .map(everydayTaskMapper::toDto));
    }

    @DeleteMapping("/deleteEverydayTask/{id}")
    public ResponseEntity<Void> deleteTask(Principal principal, @PathVariable Long id){
        everydayTaskService.deleteTask(id, userService.findByUsername(principal.getName()).get());
        return ResponseEntity.noContent().build();
    }


}
