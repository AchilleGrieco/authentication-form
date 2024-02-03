package com.example.AuthForm.security;

import com.example.AuthForm.service.AuthFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AuthFormUserDetailsService implements UserDetailsService {


    @Autowired
    AuthFormService authFormService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authFormService.getUserByEmail(username);
    }


}
