package com.hoho.cheklist.db.repository.detail;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hoho.cheklist.db.AppDBHelper;
import com.hoho.cheklist.db.ddl.checklist.ChecklistP2ItemDDL;
import com.hoho.cheklist.dto.detail.P2ItemDto;

import java.util.ArrayList;
import java.util.List;

public class P2ItemRepository {

    private final AppDBHelper helper;

    public P2ItemRepository(AppDBHelper helper) {
        this.helper = helper;
    }

    /**
     * checklist_id 기준으로 P2 항목 전체 조회
     */
    public List<P2ItemDto> findByChecklistId(long checklistId) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql =
                "SELECT "
                        + ChecklistP2ItemDDL.COL_ID + ", "
                        + ChecklistP2ItemDDL.COL_CHECKLIST_ID + ", "
                        + ChecklistP2ItemDDL.COL_ITEM_NO + ", "
                        + ChecklistP2ItemDDL.COL_LABEL + ", "
                        + ChecklistP2ItemDDL.COL_RESULT + ", "
                        + ChecklistP2ItemDDL.COL_REMARK
                        + " FROM " + ChecklistP2ItemDDL.TABLE
                        + " WHERE " + ChecklistP2ItemDDL.COL_CHECKLIST_ID + " = ?"
                        + " ORDER BY " + ChecklistP2ItemDDL.COL_ITEM_NO;

        String[] args = { String.valueOf(checklistId) };

        List<P2ItemDto> result = new ArrayList<>();

        try (Cursor c = db.rawQuery(sql, args)) {
            while (c.moveToNext()) {
                int i = 0;
                P2ItemDto dto = P2ItemDto.create(
                        c.getLong(i++),
                        c.getLong(i++),
                        c.getInt(i++),
                        c.getString(i++),
                        c.getString(i++),
                        c.getString(i++)
                );

                result.add(dto);
            }
        }

        return result;
    }
}
