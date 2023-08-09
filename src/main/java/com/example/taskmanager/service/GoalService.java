package com.example.taskmanager.service;

import com.example.taskmanager.dto.goal.GoalCreationDto;
import com.example.taskmanager.dto.goal.GoalDto;
import com.example.taskmanager.dto.goal.GoalUpdateDto;
import com.example.taskmanager.entity.Goal;
import com.example.taskmanager.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface GoalService {



    public Goal createOne(User user, Goal goal);

    Optional<Goal> findById(Long id);

    public List<Goal> getAll(Long id);

    void deleteById(Long id, User user);

    Goal updateById(Long goalId,User user, Goal goal);

}
