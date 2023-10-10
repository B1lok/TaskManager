package com.example.taskmanager.controller;


import com.example.taskmanager.dto.everydayTask.EverydayTaskCreationDto;
import com.example.taskmanager.dto.everydayTask.EverydayTaskUpdateDto;
import com.example.taskmanager.repository.EverydayTaskRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@WithMockUser("First")
public class EverydayTaskControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EverydayTaskRepository everydayTaskRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void cleanAll(){
        everydayTaskRepository.deleteAll();
        userRepository.deleteAll();
    }

    private final String url = "/myTask/everydayTasks";

    @Test
    @Sql({"/create-users.sql","/create-everydayTasks.sql"})
    public void getEverydayTasks() throws Exception {
        var resultAction = mvc.perform(get(url+"/2023-10-09"));

        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].description").value("Task 1 for First"));
    }


    @Test
    @Sql({"/create-users.sql","/create-everydayTasks.sql"})
    public void createEverydayTask() throws Exception{
        var everydayTaskCreationDto = new EverydayTaskCreationDto();
        everydayTaskCreationDto.setDescription("Task 3 for First");
        everydayTaskCreationDto.setCompleteAt(LocalDate.now());

        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        var json = objectMapper.writeValueAsString(everydayTaskCreationDto);

        mvc.perform(post("/myTask/createEverydayTask").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Task 3 for First"));
    }

    @Test
    @Sql({"/create-users.sql","/create-everydayTasks.sql"})
    public void updateEverydayTask() throws Exception{
        long correctTaskId = 1;
        long wrongTaskId = 3;

        var updateDto = new EverydayTaskUpdateDto();
        updateDto.setDescription("Updated Task1 for first");

        var json = new ObjectMapper().writeValueAsString(updateDto);

        mvc.perform(patch("/myTask/updateEverydayTask/"+correctTaskId).contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Updated Task1 for first"));

        mvc.perform(patch("/myTask/updateEverydayTask/"+wrongTaskId).contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isForbidden());
    }

    @Test
    @Sql({"/create-users.sql","/create-everydayTasks.sql"})
    public void deleteEverydayTask()throws Exception{
        long correctTaskId = 1;
        long wrongTaskId = 3;

        mvc.perform(delete("/myTask/deleteEverydayTask/"+correctTaskId))
                .andExpect(status().isNoContent());

        mvc.perform(delete("/myTask/deleteEverydayTask/"+wrongTaskId))
                .andExpect(status().isForbidden());
    }

}
