package com.example.taskmanager.repository;

import com.example.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    @Query("SELECT u FROM User u WHERE u.email = :data OR u.username = :data")
    Optional<User> findByUsernameOrEmail(@Param("data") String login);


    public Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);


    public boolean existsByUsernameOrEmail(String username, String email);

}
