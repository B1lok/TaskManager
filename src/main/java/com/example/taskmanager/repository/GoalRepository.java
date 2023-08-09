package com.example.taskmanager.repository;

import com.example.taskmanager.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {


    Optional<Goal> findById(Long id);



    List<Goal> findAllByOwnerId(Long id);
}
