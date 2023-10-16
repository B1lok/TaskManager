package com.example.taskmanager.controller;

import com.example.taskmanager.dto.goal.GoalCreationDto;
import com.example.taskmanager.dto.goal.GoalUpdateDto;
import com.example.taskmanager.repository.GoalRepository;
import com.example.taskmanager.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@WithMockUser("First")
public class GoalControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoalRepository goalRepository;

    @AfterEach
    public void cleanAll(){
        userRepository.deleteAll();
        goalRepository.deleteAll();
    }

    @Test
    @Sql({"/create-users.sql","/create-goals.sql"})
    public void getGoals() throws Exception{
        var resultAction = mvc.perform(get("/myTask/goals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Project Proposal"));
    }

    @Test
    @Sql({"/create-users.sql"})
    public void createGoal() throws Exception {
        var newGoal = new GoalCreationDto();
        newGoal.setDescription("New goal for First user");
        newGoal.setTitle("New goal");
        newGoal.setDueTo(LocalDate.now().plusDays(1));

        var json = new ObjectMapper().findAndRegisterModules().writeValueAsString(newGoal);

        mvc.perform(post("/myTask/createGoal").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New goal"));
    }

    @Test
    @Sql({"/create-users.sql","/create-goals.sql"})
    public void updateGoal() throws Exception{
        long correctGoalID = 1;
        long wrongGoalID = 3;
        String goalTitle = "Updated goal";

        var goalUpdate = new GoalUpdateDto();
        goalUpdate.setTitle(goalTitle);

        var json = new ObjectMapper().writeValueAsString(goalUpdate);

        mvc.perform(patch("/myTask/goals/"+wrongGoalID).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isForbidden());

        mvc.perform(patch("/myTask/goals/"+correctGoalID).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(goalTitle)));
    }

    @Test
    @Sql({"/create-users.sql","/create-goals.sql"})
    public void deleteGoal() throws Exception{
        long correctTaskId = 1;
        long wrongTaskId = 3;

        mvc.perform(delete("/myTask/goals/"+correctTaskId))
                .andExpect(status().isNoContent());

        mvc.perform(delete("/myTask/goals/"+wrongTaskId))
                .andExpect(status().isForbidden());
    }


}
