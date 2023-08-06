package com.example.taskmanager.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.taskmanager.entity.User;

import java.util.Optional;

public interface UserService {




    User createUser(User user);

    Optional<DecodedJWT> signIn(String login, String password);

}
