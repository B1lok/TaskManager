package com.example.taskmanager.service;


import com.example.taskmanager.dto.user.UserCreationDto;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.exception.UserAlreadyExistException;
import com.example.taskmanager.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class UserServiceTest {




    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @AfterEach
    public void cleanAll(){userRepository.deleteAll();}



    @Test
    @DisplayName("Testing creating user with taken credentials")
    @Sql("/create-users.sql")
    public void createUserTest() throws Exception{
        var user = new User();
        user.setUsername("First");
        user.setPassword("$2a$12$3xSgMf/TzXueuyN4TMikCes6FkxCdVJ4RdrwguxfitTHvxQmj3q9O");
        user.setEmail("First@gmail.com");

        assertThatThrownBy(() -> userService.createUser(user)
                ).isInstanceOf(UserAlreadyExistException.class)
                .message().contains("User already exist");
    }



    @Test
    @DisplayName("Testing updating user with taken credentials")
    @Sql("/create-users.sql")
    public void updateUserTest(){
        var user = userRepository.findById(1L).orElseThrow();
        user.setUsername("Second");
        user.setEmail("Second@gmail.com");
        assertThatThrownBy(() -> userService.updateUser(user))
                .isInstanceOf(UserAlreadyExistException.class)
                .message().contains("Second");

    }


    @Test
    @DisplayName("Testing finding by username method")
    @Sql("/create-users.sql")
    public void findByUsernameTest(){
        var user = userService.findByUsername("First");

        assertEquals(user, userRepository.findById(1L));
        assertThat(user).isPresent();

    }


}
