package com.example.taskmanager.dto.goal;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class GoalUpdateDto {

    private String title;

    private String description;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    private LocalDate dueTo;

}
