package com.example.taskmanager.controller;


import com.example.taskmanager.dto.auth.AuthRequest;
import com.example.taskmanager.dto.user.UserUpdateDto;
import com.example.taskmanager.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.stream.Stream;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserControllerTest {


    @Autowired
    private MockMvc mvc;


    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void cleanAllAfterEach() {
        userRepository.deleteAll();
    }




    @Sql("/create-users.sql")
    @DisplayName("Testing that user can get himself")
    @ParameterizedTest
    @MethodSource
    public void selfTest(String login, String password) throws Exception {

        var authRequest = new AuthRequest();
        authRequest.setPassword(password);
        authRequest.setLogin(login);

        var json = new ObjectMapper().writeValueAsString(authRequest);

        MvcResult result = mvc.perform(post("/sign-in").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
        String token = "Bearer ".concat(JsonPath.read(result.getResponse().getContentAsString(), "$.token"));

        mvc.perform(get("/myTask/self").header(HttpHeaders.AUTHORIZATION, token)).andExpect(status().isOk()).
                andExpect(jsonPath("$.username", notNullValue()))
                .andExpect(jsonPath("$.email", notNullValue()));
    }

    public static Stream<Arguments> selfTest() {
        return Stream.of(
                Arguments.of("First", "First"),
                Arguments.of("Second", "Second"),
                Arguments.of("Third", "Third")
        );
    }

    @Sql("/create-users.sql")
    @DisplayName("Testing updating user")
    @Test
    public void updateTest() throws Exception{

        var authRequest = new AuthRequest();
        authRequest.setPassword("First");
        authRequest.setLogin("First");

        var json = new ObjectMapper().writeValueAsString(authRequest);

        MvcResult result = mvc.perform(post("/sign-in").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
        String token = "Bearer ".concat(JsonPath.read(result.getResponse().getContentAsString(), "$.token"));

        var wrongUpdateRequest = new UserUpdateDto();
        wrongUpdateRequest.setUsername("Second");
        wrongUpdateRequest.setEmail("First@gmail.com");

        var wrongUpdateJson = new ObjectMapper().writeValueAsString(wrongUpdateRequest);

        mvc.perform(patch("/myTask/self/update").header(HttpHeaders.AUTHORIZATION, token).contentType(MediaType.APPLICATION_JSON).content(wrongUpdateJson))
                .andExpect(status().isBadRequest());

        var updateRequest = new UserUpdateDto();
        updateRequest.setEmail("UpdatedFirst@gmail.com");
        updateRequest.setUsername("UpdatedFirst");

        var updateJson = new ObjectMapper().writeValueAsString(updateRequest);
        mvc.perform(patch("/myTask/self/update").header(HttpHeaders.AUTHORIZATION, token).contentType(MediaType.APPLICATION_JSON).content(updateJson))
                .andExpect(status().isOk());
    }




}
