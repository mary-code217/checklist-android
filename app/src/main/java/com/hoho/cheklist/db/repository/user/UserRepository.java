package com.hoho.cheklist.db.repository.user;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hoho.cheklist.db.AppDBHelper;
import com.hoho.cheklist.db.dml.user.UsersDML;

public class UserRepository {

    private final AppDBHelper helper;

    public UserRepository(AppDBHelper helper) {
        this.helper = helper;
    }

    public boolean checkLogin(String username, String password) {
        try (SQLiteDatabase db = helper.getReadableDatabase();
             Cursor c = db.rawQuery(
                     UsersDML.findByAdmin,
                     new String[]{ username, password })) {
            return c.moveToFirst();
        }
    }
}
