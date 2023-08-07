package com.example.taskmanager.controller;


import com.example.taskmanager.dto.user.UserDto;
import com.example.taskmanager.mapper.UserMapper;
import com.example.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
@RequestMapping("/myTask")
public class UserController {



    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping("/self")
    public ResponseEntity<UserDto> getSelf(Principal principal){
        return ResponseEntity.of(userService.findByUsername(principal.getName()).map(userMapper::toDto));
    }









}
