package com.example.taskmanager.dto.auth;


import lombok.Data;

@Data
public class AuthRequest {


    private String login;

    private String password;
}
