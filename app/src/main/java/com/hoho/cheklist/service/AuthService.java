package com.hoho.cheklist.service;

import com.hoho.cheklist.db.repository.UserRepository;

public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(String username, String password) {
        return userRepository.checkLogin(username, password);
    }
}
