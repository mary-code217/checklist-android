package com.hoho.cheklist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hoho.cheklist.db.ddl.P1.P1ItemMaster;
import com.hoho.cheklist.db.ddl.P1.P1SectionMaster;
import com.hoho.cheklist.db.ddl.P2.P2ItemMaster;
import com.hoho.cheklist.db.ddl.checklist.Checklist;
import com.hoho.cheklist.db.ddl.checklist.ChecklistP1Item;
import com.hoho.cheklist.db.ddl.checklist.ChecklistP1Photo;
import com.hoho.cheklist.db.ddl.checklist.ChecklistP2Item;
import com.hoho.cheklist.db.ddl.checklist.ChecklistP2Photo;
import com.hoho.cheklist.db.ddl.user.Users;

public class AppDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "app.db";
    private static final int DB_VER = 1;

    public AppDBHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 스키마
        db.execSQL("PRAGMA foreign_keys=ON");

        db.execSQL(Users.create());
        db.execSQL(P1SectionMaster.create());
        db.execSQL(P1ItemMaster.create());
        db.execSQL(P2ItemMaster.create());
        db.execSQL(Checklist.create());
        db.execSQL(ChecklistP1Item.create());
        db.execSQL(ChecklistP1Photo.create());
        db.execSQL(ChecklistP2Item.create());
        db.execSQL(ChecklistP2Photo.create());

        ContentValues cv = new ContentValues();
        cv.put("username", "admin");
        cv.put("password", "1q2w3e4r");
        db.insert("users", null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
