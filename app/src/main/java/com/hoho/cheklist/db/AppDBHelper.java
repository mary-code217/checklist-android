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
import com.hoho.cheklist.db.ddl.P1.P1ItemMasterDDL;
import com.hoho.cheklist.db.ddl.P1.P1SectionMasterDDL;
import com.hoho.cheklist.db.ddl.P2.P2ItemMasterDDL;
import com.hoho.cheklist.db.ddl.user.UsersDDL;
import com.hoho.cheklist.db.dml.checklist.ChecklistDML;
import com.hoho.cheklist.db.dml.p1.P1ItemMasterDML;
import com.hoho.cheklist.db.dml.p1.P1SectionMasterDML;
import com.hoho.cheklist.db.dml.p2.P2ItemMasterDML;

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
            P1SectionMasterDML.insertSections(db); // P1 No.1~15 대항목
            P1ItemMasterDML.insertItem(db); // P1 1-1,1-2,1-3 .. 하위항목
            P2ItemMasterDML.insertP2Item(db); // P2 기술점검항목

            ChecklistDML.insertDummyChecklists(db); // 메인페이지 더미 데이터

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void setAdmin(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put("username", "admin");
        cv.put("password", "1234");
        db.insert("users", null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
