package com.hoho.cheklist.service.user;

import com.hoho.cheklist.db.AppDBHelper;
import com.hoho.cheklist.db.repository.user.UserRepository;

public class AuthService {

    private final UserRepository userRepository;

    public AuthService(AppDBHelper dbHelper) {
        this.userRepository = new UserRepository(dbHelper);
    }

    public boolean login(String username, String password) {
        return userRepository.checkLogin(username, password);
    }
}
