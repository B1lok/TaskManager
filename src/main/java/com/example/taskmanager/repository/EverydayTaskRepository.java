package com.example.taskmanager.repository;

import com.example.taskmanager.entity.EverydayTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface EverydayTaskRepository extends JpaRepository<EverydayTask, Long> {


    List<EverydayTask> findAllByUserId(Long id);

    Optional<EverydayTask> findById(Long id);


    @Query(value = "SELECT * from everyday_task where complete_at = :date and user_id = :id", nativeQuery = true)
    List<EverydayTask> findAllTasksByDate(@Param("id") Long id, @Param("date") LocalDate date);

}
