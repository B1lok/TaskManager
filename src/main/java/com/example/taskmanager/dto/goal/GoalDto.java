package com.example.taskmanager.dto.goal;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GoalDto {

    private String title;

    private Long id;


    private String description;


    private LocalDate dueDate;




}
