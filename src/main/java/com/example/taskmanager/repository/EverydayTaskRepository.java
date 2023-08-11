package com.example.taskmanager.repository;

import com.example.taskmanager.entity.EverydayTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EverydayTaskRepository extends JpaRepository<EverydayTask, Long> {


    List<EverydayTask> findAllByUserId(Long id);

    Optional<EverydayTask> findById(Long id);



}
