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



    @Query("select u from User u where u.username = :username and u.id != :id")
    Optional<User> isUsernameInUse(@Param("username") String username, @Param("id") Long id);


    @Query("select u from User u where u.email = :email and u.id != :id")
    Optional<User> isEmailInUse(@Param("email") String email, @Param("id") Long id);



    public Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);


    public boolean existsByUsernameOrEmail(String username, String email);

}
