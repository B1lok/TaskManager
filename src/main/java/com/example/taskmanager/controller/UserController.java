package com.example.taskmanager.controller;


import com.example.taskmanager.dto.user.UserDto;
import com.example.taskmanager.dto.user.UserUpdateDto;
import com.example.taskmanager.mapper.UserMapper;
import com.example.taskmanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/self/update")
    public ResponseEntity<UserDto> updateSelf(Principal principal,@Valid @RequestBody UserUpdateDto userUpdateDto){

        return ResponseEntity.of(userService.findByUsername(principal.getName())
                .map(user -> userMapper.userUpdate(userUpdateDto, user))
                .map(userService::updateUser)
                .map(userMapper::toDto));

    }
}
