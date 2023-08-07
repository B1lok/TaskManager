package com.example.taskmanager.controller;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.taskmanager.dto.auth.AuthRequest;
import com.example.taskmanager.dto.auth.AuthResponse;
import com.example.taskmanager.dto.user.UserCreationDto;
import com.example.taskmanager.dto.user.UserDto;
import com.example.taskmanager.mapper.AuthMapper;
import com.example.taskmanager.mapper.UserMapper;
import com.example.taskmanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {


    private final UserService userService;

    private final UserMapper userMapper;

    private final AuthMapper authMapper;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody UserCreationDto userCreationDto){
        userService.createUser(userMapper.toEntity(userCreationDto));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> signIn(@Valid @RequestBody AuthRequest authRequest){

        return ResponseEntity.of(userService.signIn(authRequest.getLogin(), authRequest.getPassword()).map(authMapper::toAuthResponse));
    }




}
