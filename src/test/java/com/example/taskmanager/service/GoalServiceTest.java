package com.example.taskmanager.service;

import com.example.taskmanager.repository.GoalRepository;
import com.example.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class GoalServiceTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private GoalService goalService;

    @AfterEach
    public void cleanAll(){
        userRepository.deleteAll();
        goalRepository.deleteAll();
    }

    @Test
    @DisplayName("Getting all goals by owner id test")
    @Sql({"/create-users.sql","/create-goals.sql"})
    public void getAllTest(){
        var user = userRepository.findById(1L).orElseThrow();

        var userGoals = goalService.getAll(user.getId());

        var goal = goalService.findById(1L).orElseThrow();

        assertEquals(2, userGoals.size());


    }


}
