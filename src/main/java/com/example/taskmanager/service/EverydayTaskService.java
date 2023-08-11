package com.example.taskmanager.service;


import com.example.taskmanager.dto.everydayTask.EverydayTaskDto;
import com.example.taskmanager.entity.EverydayTask;
import com.example.taskmanager.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface EverydayTaskService {


    List<EverydayTask> getAll(Long id);

    Optional<EverydayTask> findById(Long id);

    void deleteTask(Long id, User user);

    EverydayTask createTask(EverydayTask everydayTask, User user);

    EverydayTask updateTask(EverydayTask everydayTask, User user, Long taskId);


}
