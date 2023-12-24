package com.example.taxi_app_final.service;

import com.example.taxi_app_final.model.User;

public interface AuthService {
    User login(String username, String password);
}
