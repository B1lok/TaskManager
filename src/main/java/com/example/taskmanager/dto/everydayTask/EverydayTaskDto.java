package com.example.taskmanager.dto.everydayTask;


import jakarta.persistence.Column;
import lombok.Data;

@Data
public class EverydayTaskDto {


    private Long id;

    private String description;

}
