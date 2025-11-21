package com.hoho.cheklist.db.repository.detail;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hoho.cheklist.db.AppDBHelper;
import com.hoho.cheklist.db.ddl.checklist.ChecklistP1ItemDDL;
import com.hoho.cheklist.db.ddl.checklist.ChecklistP1PhotoDDL;
import com.hoho.cheklist.dto.detail.P1PhotoDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P1PhotoRepository {

    private final AppDBHelper helper;

    public P1PhotoRepository(AppDBHelper helper) {
        this.helper = helper;
    }

    /**
     * checklist_id 기준으로 p1 사진 전체 조회
     * 결과: p1_item_id → 사진 목록
     */
    public Map<Long, List<P1PhotoDto>> findGroupedByChecklistId(long checklistId) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql =
                "SELECT "
                        + "p." + ChecklistP1PhotoDDL.COL_ID + ", "
                        + "p." + ChecklistP1PhotoDDL.COL_P1_ITEM_ID + ", "
                        + "p." + ChecklistP1PhotoDDL.COL_PHOTO_PATH
                        + " FROM " + ChecklistP1PhotoDDL.TABLE + " p"
                        + " JOIN " + ChecklistP1ItemDDL.TABLE + " i"
                        + "   ON p." + ChecklistP1PhotoDDL.COL_P1_ITEM_ID
                        + "    = i." + ChecklistP1ItemDDL.COL_ID
                        + " WHERE i." + ChecklistP1ItemDDL.COL_CHECKLIST_ID + " = ?"
                        + " ORDER BY p." + ChecklistP1PhotoDDL.COL_ID;

        String[] args = { String.valueOf(checklistId) };

        Map<Long, List<P1PhotoDto>> map = new HashMap<>();

        try (Cursor c = db.rawQuery(sql, args)) {
            while (c.moveToNext()) {
                int i = 0;
                long id = c.getLong(i++);
                long p1ItemId = c.getLong(i++);
                String path = c.getString(i++);

                P1PhotoDto dto = P1PhotoDto.create(id, p1ItemId, path);

                List<P1PhotoDto> list = map.computeIfAbsent(p1ItemId, k -> new ArrayList<>());
                list.add(dto);
            }
        }

        return map;
    }

    /**
     * 사진 1장 저장
     * @return 생성된 row의 id (실패 시 -1)
     */
    public long insert(long p1ItemId, String photoPath) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ChecklistP1PhotoDDL.COL_P1_ITEM_ID, p1ItemId);
        values.put(ChecklistP1PhotoDDL.COL_PHOTO_PATH, photoPath);

        return db.insert(ChecklistP1PhotoDDL.TABLE, null, values);
    }

    /**
     * p1_item_id 기준으로 사진 전체 조회
     */
    public List<P1PhotoDto> findByP1ItemId(long p1ItemId) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql =
                "SELECT "
                        + ChecklistP1PhotoDDL.COL_ID + ", "
                        + ChecklistP1PhotoDDL.COL_P1_ITEM_ID + ", "
                        + ChecklistP1PhotoDDL.COL_PHOTO_PATH
                        + " FROM " + ChecklistP1PhotoDDL.TABLE
                        + " WHERE " + ChecklistP1PhotoDDL.COL_P1_ITEM_ID + " = ?"
                        + " ORDER BY " + ChecklistP1PhotoDDL.COL_ID;

        String[] args = { String.valueOf(p1ItemId) };

        List<P1PhotoDto> result = new ArrayList<>();

        try (Cursor c = db.rawQuery(sql, args)) {
            while (c.moveToNext()) {
                int i = 0;
                P1PhotoDto dto = P1PhotoDto.create(
                        c.getLong(i++),   // id
                        c.getLong(i++),   // p1_item_id
                        c.getString(i++)  // photo_path
                );
                result.add(dto);
            }
        }

        return result;
    }

    /**
     * p1_item_id 기준으로 현재 사진 개수 (4장 제한 체크용)
     */
    public int countByP1ItemId(long p1ItemId) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql =
                "SELECT COUNT(*)"
                        + " FROM " + ChecklistP1PhotoDDL.TABLE
                        + " WHERE " + ChecklistP1PhotoDDL.COL_P1_ITEM_ID + " = ?";

        String[] args = { String.valueOf(p1ItemId) };

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

        String where = ChecklistP1PhotoDDL.COL_ID + " = ?";
        String[] args = { String.valueOf(id) };

        return db.delete(ChecklistP1PhotoDDL.TABLE, where, args);
    }

    /**
     * 특정 p1 항목의 사진 전체 삭제
     */
    public int deleteAllByP1ItemId(long p1ItemId) {
        SQLiteDatabase db = helper.getWritableDatabase();

        String where = ChecklistP1PhotoDDL.COL_P1_ITEM_ID + " = ?";
        String[] args = { String.valueOf(p1ItemId) };

        return db.delete(ChecklistP1PhotoDDL.TABLE, where, args);
    }
}
