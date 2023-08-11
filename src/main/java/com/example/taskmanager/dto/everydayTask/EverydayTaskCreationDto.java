package com.example.taskmanager.dto.everydayTask;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EverydayTaskCreationDto {



    @NotBlank(message = "Specify your task")
    @Size(max = 1000, message = "Description can't exceed 1000 characters")
    private String description;

}
