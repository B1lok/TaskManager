package com.example.taskmanager.dto.goal;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class GoalDto implements Serializable {

    private String title;

    private Long id;


    private String description;


    private LocalDate dueDate;




}
