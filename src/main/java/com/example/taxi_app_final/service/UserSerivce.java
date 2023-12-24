package com.example.taxi_app_final.service;


import com.example.taxi_app_final.model.Role;
import com.example.taxi_app_final.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserSerivce {

    User register(String username, String password, String repeatPassword, String name, String surname, Role role);

    UserDetails loadUserByUsername(String s);

}
