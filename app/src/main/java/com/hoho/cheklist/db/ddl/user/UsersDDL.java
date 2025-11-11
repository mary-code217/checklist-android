package com.hoho.cheklist.db.ddl.user;

/**
 * 유저 DDL
 */
public final class UsersDDL {

    private static final String TABLE = "users"; // 테이블 이름

    private static final String COL_ID = "id"; // PK
    private static final String COL_USERNAME = "username"; // 아이디
    private static final String COL_PASSWORD = "password"; // 비밀번호

    private UsersDDL() {}

    public static String create() {
        return "CREATE TABLE " + TABLE + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_USERNAME + " TEXT UNIQUE NOT NULL, "
                + COL_PASSWORD + " TEXT NOT NULL"
                + ");";
    }
}
