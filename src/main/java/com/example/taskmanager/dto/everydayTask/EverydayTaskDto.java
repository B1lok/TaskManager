package com.example.taskmanager.dto.everydayTask;


import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class EverydayTaskDto implements Serializable {


    private Long id;

    private String description;

    private LocalDate completeAt;

}
