package com.example.AuthForm.service;


import com.example.AuthForm.model.AuthFormRepository;
import com.example.AuthForm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthFormService {

    @Autowired
    AuthFormRepository authFormRepository;

    public List<User> getAllUsers() {
        return authFormRepository.getAllUsers();
    }


    public User signUp(String firstName, String lastName, String email, String password) {
       return authFormRepository.saveAndFlush(new User(firstName, lastName, email, password));
    }

    public User getUserByEmail(String email) {
        return authFormRepository.getUserByEmail(email);
    }
}
