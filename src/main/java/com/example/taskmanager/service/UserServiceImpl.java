package com.example.taskmanager.service;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.exception.InvalidPasswordException;
import com.example.taskmanager.exception.UserAlreadyExistException;
import com.example.taskmanager.exception.UserNotFoundException;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    public User createUser(User user) {
        if (!userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } else {
            throw new UserAlreadyExistException("User already exist");
        }
    }


    @Override
    @Transactional
    public Optional<DecodedJWT> signIn(String login, String password) {

        var user = userRepository.findByUsernameOrEmail(login)
                .orElseThrow(() -> new UserNotFoundException("User with login %s doesnt exist".formatted(login)));
        if (!passwordEncoder.matches(password, user.getPassword())) throw new InvalidPasswordException("Invalid Password");
        return jwtService.verifyAccessToken(jwtService.createJwtToken(user));
    }
}
