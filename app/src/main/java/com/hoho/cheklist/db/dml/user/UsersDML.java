package com.hoho.cheklist.db.dml.user;

public final class UsersDML {

    private UsersDML() {}

    public static final String findByAdmin = "SELECT 1 FROM users WHERE username=? AND password=? LIMIT 1";

}
