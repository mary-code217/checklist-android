package com.hoho.cheklist.db.repository.detail;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hoho.cheklist.db.AppDBHelper;
import com.hoho.cheklist.db.ddl.checklist.ChecklistDDL;
import com.hoho.cheklist.dto.checklist.ChecklistEntity;
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

    /**
     * checklist 헤더 1건 INSERT
     * @param entity 저장할 엔티티 (id는 사용하지 않음)
     * @return 생성된 PK(id), 실패 시 -1L
     */
    public long insert(ChecklistEntity entity) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        // PK(id)는 AUTOINCREMENT라 넣지 않는다
        values.put(ChecklistDDL.COL_TITLE,        entity.getTitle());
        values.put(ChecklistDDL.COL_INSPECTED_AT, entity.getInspectedAt());
        values.put(ChecklistDDL.COL_DEPARTMENT,   entity.getDepartment());
        values.put(ChecklistDDL.COL_SYSTEM_NAME,  entity.getSystemName());
        values.put(ChecklistDDL.COL_INSPECTOR,    entity.getInspector());
        values.put(ChecklistDDL.COL_STAGE,        entity.getStage());
        values.put(ChecklistDDL.COL_CREATED_AT,   entity.getCreatedAt());
        values.put(ChecklistDDL.COL_UPDATED_AT,   entity.getUpdatedAt());

        // insert() 리턴값이 새로 생성된 row의 id
        return db.insert(ChecklistDDL.TABLE, null, values);
    }
}
