package com.example.taxi_app_final.service.impl;

import com.example.taxi_app_final.model.User;
import com.example.taxi_app_final.repository.UserRepository;
import com.example.taxi_app_final.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if (username==null || username.isEmpty() || password==null || password.isEmpty()) {
            throw null;
        }
        return userRepository.findByUsernameAndPassword(username,
                password).orElseThrow(null);
    }

}
