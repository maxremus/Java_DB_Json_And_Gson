package com.example.demo_json_and_gson.repository;

import com.example.demo_json_and_gson.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User AS u " +
            "JOIN u.soldProducts AS sp " +
            "WHERE sp.buyer IS NOT NULL " +
            "ORDER BY u.lastName, u.firstName")
    List<User> findWithSoldProductsOrderByLastName();
}
