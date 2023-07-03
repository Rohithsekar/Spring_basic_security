package com.example.Spring_basic_security.service;

import com.example.Spring_basic_security.model.RequestEntity;
import com.example.Spring_basic_security.model.User;
import com.example.Spring_basic_security.repository.RequestEntityRepository;
import com.example.Spring_basic_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RequestEntityRepository requestEntityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<User> getUsers() {
        return repository.findAll();
    }

    public User getUserById(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<User> getUserByProfession(String profession) {
        return repository.findByProfession(profession);
    }

    public String addRequestEntity(RequestEntity requestEntity){
        requestEntity.setPassword(passwordEncoder.encode(requestEntity.getPassword()));
        requestEntityRepository.save(requestEntity);
        return "User added to records";
    }

}
