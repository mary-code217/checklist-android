package com.hoho.cheklist.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserRepository {

    private final AppDBHelper helper;

    public UserRepository(AppDBHelper helper) {
        this.helper = helper;
    }

    // 아이디/비밀번호 일치여부 확인
    public boolean checkLogin(String username, String password) {
        try (SQLiteDatabase db = helper.getReadableDatabase();
             Cursor c = db.rawQuery(
                     "SELECT 1 FROM users WHERE username=? AND password=? LIMIT 1",
                     new String[]{ username, password })) {
            return c.moveToFirst();
        }
    }
}
