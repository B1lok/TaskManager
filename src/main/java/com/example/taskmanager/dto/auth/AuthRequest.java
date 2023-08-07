package com.example.taskmanager.dto.auth;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {



    @NotBlank(message = "Specify Login")
    private String login;


    @NotBlank(message = "Specify Password")
    private String password;
}
