package com.example.taskmanager.controller;


import com.example.taskmanager.dto.auth.AuthRequest;
import com.example.taskmanager.dto.user.UserCreationDto;
import com.example.taskmanager.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
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

import java.util.stream.Stream;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class AuthControllerTest {


    @Autowired
    private MockMvc mvc;


    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void cleanAll() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("All users should have access to auth endpoints")
    void allUsersShouldHaveAccessToAuthEndpoints() throws Exception {
        mvc.perform(post("/sign-up")).andExpect(status().isBadRequest());
        mvc.perform(post("/sign-in")).andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("Sign up testing")
    void signUpTesting() throws Exception {

        var user = new UserCreationDto();
        user.setUsername("John");
        user.setEmail("John@gmail.com");
        user.setPassword("john");

        var jsonUser = new ObjectMapper().writeValueAsString(user);

        mvc.perform(post("/sign-up")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonUser))
                .andExpect(status().is2xxSuccessful());

        var userWithSameCredentials = new UserCreationDto();
        userWithSameCredentials.setUsername("John");
        userWithSameCredentials.setEmail("John@gmail.com");
        userWithSameCredentials.setPassword("john");

        var jsonUserWithSameCredentials = new ObjectMapper().writeValueAsString(userWithSameCredentials);

        mvc.perform(post("/sign-up")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonUserWithSameCredentials))
                .andExpect(status().isBadRequest());
    }


    @ParameterizedTest
    @DisplayName("Sign in testing")
    @MethodSource
    @Sql("/create-users.sql")
    void signInTesting(String login, String password) throws Exception {

        var user = new AuthRequest();
        user.setLogin(login);
        user.setPassword(password);

        var json = new ObjectMapper().writeValueAsString(user);

        mvc.perform(post("/sign-in").contentType(MediaType.APPLICATION_JSON).content(json)).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.token", notNullValue()));
    }

    private static Stream<Arguments> signInTesting() {
        return Stream.of(
                Arguments.of("First", "First"),
                Arguments.of("Second", "Second"),
                Arguments.of("Third", "Third")
        );
    }


    @ParameterizedTest
    @DisplayName("Sign in testing with wrong login")
    @MethodSource
    @Sql("/create-users.sql")
    void signInTestingWrongLogin(String login, String password) throws Exception {

        var user = new AuthRequest();
        user.setLogin(login);
        user.setPassword(password);

        var json = new ObjectMapper().writeValueAsString(user);

        mvc.perform(post("/sign-in").contentType(MediaType.APPLICATION_JSON).content(json)).
                andExpect(status().isNotFound());


    }

    private static Stream<Arguments> signInTestingWrongLogin() {
        return Stream.of(
                Arguments.of("Invalid Login", "First"),
                Arguments.of("Invalid Login", "Second"),
                Arguments.of("invalid Login", "Third")
        );
    }


    @ParameterizedTest
    @DisplayName("Sign in testing with wrong password")
    @MethodSource
    @Sql("/create-users.sql")
    void signInTestingWrongPassword(String login, String password) throws Exception {

        var user = new AuthRequest();
        user.setLogin(login);
        user.setPassword(password);

        var json = new ObjectMapper().writeValueAsString(user);

        mvc.perform(post("/sign-in").contentType(MediaType.APPLICATION_JSON).content(json)).
                andExpect(status().isBadRequest());


    }

    private static Stream<Arguments> signInTestingWrongPassword() {
        return Stream.of(
                Arguments.of("First", "Invalid Password"),
                Arguments.of("Second", "Invalid Password"),
                Arguments.of("Third", "Invalid Password")
        );
    }


}
