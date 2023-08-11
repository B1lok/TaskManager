package com.example.taskmanager.dto.everydayTask;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EverydayTaskUpdateDto {



    @NotBlank(message = "Specify your task")
    @Size(max = 1000, message = "Description can't exceed 1000 characters")
    private String description;


}
