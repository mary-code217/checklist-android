package com.hoho.cheklist.db.repository.detail;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hoho.cheklist.db.AppDBHelper;
import com.hoho.cheklist.db.ddl.checklist.ChecklistDDL;
import com.hoho.cheklist.dto.detail.ChecklistDetailDto;

public class ChecklistHeaderRepository {
    private final AppDBHelper helper;

    public ChecklistHeaderRepository(AppDBHelper helper) {
        this.helper = helper;
    }

    /**
     * checklist 헤더 1건 조회
     * 없으면 null
     */
    public ChecklistDetailDto findHeader(long checklistId) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql =
                "SELECT "
                        + ChecklistDDL.COL_ID + ", "
                        + ChecklistDDL.COL_TITLE + ", "
                        + ChecklistDDL.COL_INSPECTED_AT + ", "
                        + ChecklistDDL.COL_DEPARTMENT + ", "
                        + ChecklistDDL.COL_SYSTEM_NAME + ", "
                        + ChecklistDDL.COL_INSPECTOR + ", "
                        + ChecklistDDL.COL_STAGE + ", "
                        + ChecklistDDL.COL_CREATED_AT + ", "
                        + ChecklistDDL.COL_UPDATED_AT
                        + " FROM " + ChecklistDDL.TABLE
                        + " WHERE " + ChecklistDDL.COL_ID + " = ?";

        String[] args = { String.valueOf(checklistId) };

        try (Cursor c = db.rawQuery(sql, args)) {
            if (!c.moveToFirst()) {
                return null;
            }

            int i = 0;

            return ChecklistDetailDto.create(
                    c.getLong(i++),
                    c.getString(i++),
                    c.getString(i++),
                    c.getString(i++),
                    c.getString(i++),
                    c.getString(i++),
                    c.getInt(i++),
                    c.getString(i++),
                    c.getString(i)
            );
        }
    }
}
