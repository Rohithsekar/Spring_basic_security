package com.example.Spring_basic_security.controller;

import com.example.Spring_basic_security.model.RequestEntity;
import com.example.Spring_basic_security.model.User;
import com.example.Spring_basic_security.service.JWTService;
import com.example.Spring_basic_security.service.RequestEntityUserDetailsService;
import com.example.Spring_basic_security.service.UserService;
import com.example.Spring_basic_security.transfer.AuthRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.beans.factory.annotation.Autowired;
;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class ApplicationController {

    @Autowired
    private RequestEntityUserDetailsService requestEntityUserDetailsService;

    @Autowired
    private UserService service;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String greet(){
        return "Not secured endpoint";
    }

    @PostMapping("/new")
    public String addNewRequestEntity(@RequestBody  RequestEntity requestEntity){
        return service.addRequestEntity(requestEntity);
    }
    @GetMapping("/getAll")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> getAllUsers(){
        return service.getUsers();
    }

    @GetMapping("/getById/{id}")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
    public User getById(@PathVariable int id){ return service.getUserById(id);}
    @GetMapping("/getByProfession/{profession}")
    public List<User> getByProfession(@PathVariable String profession){
        return service.getUserByProfession(profession);
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody  AuthRequest authRequest){
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(),authRequest.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(authRequest.getUserName());
        }
        else{
            throw new UsernameNotFoundException("invalid user request");
        }

    }




}
