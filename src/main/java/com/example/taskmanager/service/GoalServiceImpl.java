package com.example.taskmanager.service;

import com.example.taskmanager.dto.goal.GoalUpdateDto;
import com.example.taskmanager.entity.Goal;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.exception.ForbiddenAccessException;
import com.example.taskmanager.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService{


    private final GoalRepository goalRepository;


    @Override
    @Transactional
    public Optional<Goal> findById(Long id) {
        return goalRepository.findById(id);
    }

    @Override
    public List<Goal> getAll(Long id) {
        return goalRepository.findAllByOwnerId(id);
    }

    @Override
    @Transactional
    public Goal createOne(User user, Goal goal) {
        user.createGoal(goal);
        return goalRepository.save(goal);
    }

    @Override
    @Transactional
    public Goal updateById(Long goalId,User user, Goal goal) {
        if (isUserGoalCreator(goalId, user)){
            return goalRepository.save(goal);
        } else {
            throw new ForbiddenAccessException("You can not modify this goal");
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id, User user) {
        if(isUserGoalCreator(id, user)){
            goalRepository.deleteById(id);
        } else {
            throw new ForbiddenAccessException("You can not delete this goal");
        }

    }

    private boolean isUserGoalCreator(Long goalId, User user){
        return goalRepository.findById(goalId)
                .map(goal -> goal.getOwner().equals(user))
                .orElse(false);
    }


}
