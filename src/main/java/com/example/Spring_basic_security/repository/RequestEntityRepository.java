package com.example.Spring_basic_security.repository;

import com.example.Spring_basic_security.model.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RequestEntityRepository extends JpaRepository<RequestEntity,Integer> {

    Optional<RequestEntity> findByUserName(String username);
}
