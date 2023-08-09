package com.example.taskmanager.controller;


import com.example.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserControllerTest {



    @Autowired
    private MockMvc mvc;


    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void cleanAll() {
        userRepository.deleteAll();
    }





}
