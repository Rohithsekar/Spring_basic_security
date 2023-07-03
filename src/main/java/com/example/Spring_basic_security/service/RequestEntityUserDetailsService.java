package com.example.Spring_basic_security.service;

import com.example.Spring_basic_security.model.RequestEntity;
import com.example.Spring_basic_security.repository.RequestEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RequestEntityUserDetailsService implements UserDetailsService {
    @Autowired
    private RequestEntityRepository repository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<RequestEntity> requestEntity = repository.findByUserName(username);
        return requestEntity.map(RequestEntityRequestDetails::new).orElseThrow(()->new UsernameNotFoundException("username not found:"+username));
   }




}
