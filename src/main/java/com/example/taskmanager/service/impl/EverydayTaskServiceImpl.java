package com.example.taskmanager.service.impl;

import com.example.taskmanager.entity.EverydayTask;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.exception.ForbiddenAccessException;
import com.example.taskmanager.repository.EverydayTaskRepository;
import com.example.taskmanager.service.EverydayTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class EverydayTaskServiceImpl implements EverydayTaskService {


    private final EverydayTaskRepository everydayTaskRepository;

    @Scheduled(cron = "0 0 0 * * * ")
    @Transactional
    public void resettingEverydayTasks() {
        List<EverydayTask> outDatedTasks = everydayTaskRepository.findAll().stream()
                .filter(task -> task.getCompleteAt().isBefore(LocalDate.now()))
                .toList();
        everydayTaskRepository.deleteAll(outDatedTasks);
    }


    @Override
    public List<EverydayTask> getAllByDate(Long id, LocalDate date) {
        return everydayTaskRepository.findAllTasksByDate(id, date);
    }

    @Override
    @Transactional
    public EverydayTask createTask(EverydayTask everydayTask, User user) {
        user.createEverydayTask(everydayTask);
        return everydayTaskRepository.save(everydayTask);
    }

    @Override
    @Transactional
    public Optional<EverydayTask> findById(Long id) {
        return everydayTaskRepository.findById(id);
    }

    @Override
    @Transactional
    public EverydayTask updateTask(EverydayTask everydayTask, User user, Long taskId) {
        if (isUserEverydayTaskCreator(user, taskId)) {
            return everydayTaskRepository.save(everydayTask);
        } else {
            throw new ForbiddenAccessException("You can not modify this task");
        }
    }


    @Override
    @Transactional
    public void deleteTask(Long id, User user) {
        if (isUserEverydayTaskCreator(user, id)) {
            everydayTaskRepository.deleteById(id);
        } else {
            throw new ForbiddenAccessException("You can not delete this task");
        }
    }

    private boolean isUserEverydayTaskCreator(User user, Long taskId) {
        return everydayTaskRepository.findById(taskId)
                .map(task -> task.getUser().equals(user))
                .orElse(false);
    }
}
