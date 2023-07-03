package com.example.Spring_basic_security.repository;

import com.example.Spring_basic_security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    List<User> findByProfession(String profession); //case-sensitive
}
