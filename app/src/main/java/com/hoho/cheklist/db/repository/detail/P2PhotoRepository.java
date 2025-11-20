package com.hoho.cheklist.db.repository.detail;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hoho.cheklist.db.AppDBHelper;
import com.hoho.cheklist.db.ddl.checklist.ChecklistP2PhotoDDL;
import com.hoho.cheklist.dto.detail.P2PhotoDto;

import java.util.ArrayList;
import java.util.List;

public class P2PhotoRepository {

    private final AppDBHelper helper;

    public P2PhotoRepository(AppDBHelper helper) {
        this.helper = helper;
    }

    /**
     * checklist_id 기준으로 P2 공통 사진 전체 조회
     */
    public List<P2PhotoDto> findByChecklistId(long checklistId) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql =
                "SELECT "
                        + ChecklistP2PhotoDDL.COL_ID + ", "
                        + ChecklistP2PhotoDDL.COL_CHECKLIST_ID + ", "
                        + ChecklistP2PhotoDDL.COL_PHOTO_PATH
                        + " FROM " + ChecklistP2PhotoDDL.TABLE
                        + " WHERE " + ChecklistP2PhotoDDL.COL_CHECKLIST_ID + " = ?"
                        + " ORDER BY " + ChecklistP2PhotoDDL.COL_ID;

        String[] args = { String.valueOf(checklistId) };

        List<P2PhotoDto> result = new ArrayList<>();

        try (Cursor c = db.rawQuery(sql, args)) {
            while (c.moveToNext()) {
                int i = 0;
                P2PhotoDto dto = P2PhotoDto.create(
                        c.getLong(i++),
                        c.getLong(i++),
                        c.getString(i++)
                );

                result.add(dto);
            }
        }

        return result;
    }

    /**
     * 사진 1장 저장
     * @return 생성된 row의 id (실패 시 -1)
     */
    public long insert(long checklistId, String photoPath) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ChecklistP2PhotoDDL.COL_CHECKLIST_ID, checklistId);
        values.put(ChecklistP2PhotoDDL.COL_PHOTO_PATH, photoPath);

        return db.insert(ChecklistP2PhotoDDL.TABLE, null, values);
    }

    /**
     * checklist_id 기준으로 사진 개수 (4장 제한 체크용)
     */
    public int countByChecklistId(long checklistId) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql =
                "SELECT COUNT(*)"
                        + " FROM " + ChecklistP2PhotoDDL.TABLE
                        + " WHERE " + ChecklistP2PhotoDDL.COL_CHECKLIST_ID + " = ?";

        String[] args = { String.valueOf(checklistId) };

        try (Cursor c = db.rawQuery(sql, args)) {
            if (c.moveToFirst()) {
                return c.getInt(0);
            }
        }
        return 0;
    }

    /**
     * 사진 1장 삭제 (id 기준)
     */
    public int deleteById(long id) {
        SQLiteDatabase db = helper.getWritableDatabase();

        String where = ChecklistP2PhotoDDL.COL_ID + " = ?";
        String[] args = { String.valueOf(id) };

        return db.delete(ChecklistP2PhotoDDL.TABLE, where, args);
    }

    /**
     * 특정 checklist의 사진 전체 삭제
     */
    public int deleteAllByChecklistId(long checklistId) {
        SQLiteDatabase db = helper.getWritableDatabase();

        String where = ChecklistP2PhotoDDL.COL_CHECKLIST_ID + " = ?";
        String[] args = { String.valueOf(checklistId) };

        return db.delete(ChecklistP2PhotoDDL.TABLE, where, args);
    }
}
