package com.example.taskmanager.dto.everydayTask;


import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class EverydayTaskCreationDto {



    @NotBlank(message = "Specify your task")
    @Size(max = 1000, message = "Description can't exceed 1000 characters")
    private String description;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    @NotNull
    private LocalDate completeAt;

}
