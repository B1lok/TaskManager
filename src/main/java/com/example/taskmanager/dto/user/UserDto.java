package com.example.taskmanager.dto.user;


import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    private Long id;

    private String username;

    private String email;


}
