package com.hoho.cheklist.db.dml.dummy;

import android.database.sqlite.SQLiteDatabase;

public class P1PhotoDummyDML {
    public static void insertDummyP1Photo(SQLiteDatabase db) {
        for (int p1ItemId = 1; p1ItemId <= 20; p1ItemId++) {
            String sql =
                    "INSERT INTO checklist_p1_photo (" +
                            "p1_item_id, photo_path" +
                            ") VALUES (" +
                            p1ItemId + ", " +
                            "'img/logo.png'" +
                            ");";

            db.execSQL(sql);
        }
    }
}
