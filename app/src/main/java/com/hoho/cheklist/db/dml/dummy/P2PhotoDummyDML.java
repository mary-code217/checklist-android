package com.hoho.cheklist.db.dml.dummy;

import android.database.sqlite.SQLiteDatabase;

public class P2PhotoDummyDML {
    public static void insertDummyP2Photo(SQLiteDatabase db) {
        for (int checklistId = 1; checklistId <= 20; checklistId++) {
            String sql =
                    "INSERT INTO checklist_p2_photo (" +
                            "checklist_id, photo_path" +
                            ") VALUES (" +
                            checklistId + ", " +
                            "'img/logo.png'" +
                            ");";

            db.execSQL(sql);
        }
    }
}
