package com.hoho.cheklist.db.repository.detail;

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
     * checklist_id 기준으로 P1 사진 전체 조회
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
}
