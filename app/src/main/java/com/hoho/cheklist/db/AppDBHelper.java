package com.hoho.cheklist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hoho.cheklist.db.ddl.checklist.ChecklistDDL;
import com.hoho.cheklist.db.ddl.checklist.ChecklistP1ItemDDL;
import com.hoho.cheklist.db.ddl.checklist.ChecklistP1PhotoDDL;
import com.hoho.cheklist.db.ddl.checklist.ChecklistP2ItemDDL;
import com.hoho.cheklist.db.ddl.checklist.ChecklistP2PhotoDDL;
import com.hoho.cheklist.db.ddl.p1.P1ItemMasterDDL;
import com.hoho.cheklist.db.ddl.p1.P1SectionMasterDDL;
import com.hoho.cheklist.db.ddl.p2.P2ItemMasterDDL;
import com.hoho.cheklist.db.ddl.user.UsersDDL;
import com.hoho.cheklist.db.dml.p1.P1SectionMasterDML;

public class AppDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "app.db";
    private static final int DB_VER = 1;

    public AppDBHelper(Context ctx) {
        super(ctx.getApplicationContext(), DB_NAME, null, DB_VER);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
        db.enableWriteAheadLogging(); // 쓰기 동시성/성능 향상
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            // DDL
            db.execSQL(UsersDDL.create());
            db.execSQL(P1SectionMasterDDL.create());
            db.execSQL(P1ItemMasterDDL.create());
            db.execSQL(P2ItemMasterDDL.create());
            db.execSQL(ChecklistDDL.create());
            db.execSQL(ChecklistP1ItemDDL.create());
            db.execSQL(ChecklistP1PhotoDDL.create());
            db.execSQL(ChecklistP2ItemDDL.create());
            db.execSQL(ChecklistP2PhotoDDL.create());

            // DML
            setAdmin(db); // admin 계정
            setP1SectionMaster(db); // P1 No.1~15 대항목



            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void setAdmin(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put("username", "admin");
        cv.put("password", "1q2w3e4r");
        db.insert("users", null, cv);
    }

    private void setP1SectionMaster(SQLiteDatabase db) {
        P1SectionMasterDML.insertSections(db);
    }

    private void setP1ItemMaster(SQLiteDatabase db) {

    }

    private void setP2ItemMaster(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
