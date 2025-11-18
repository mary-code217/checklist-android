package com.hoho.cheklist.db.repository.detail;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hoho.cheklist.db.AppDBHelper;
import com.hoho.cheklist.db.ddl.checklist.ChecklistP1ItemDDL;
import com.hoho.cheklist.dto.detail.P1ItemDto;

import java.util.ArrayList;
import java.util.List;

public class P1ItemRepository {
    private final AppDBHelper helper;

    public P1ItemRepository(AppDBHelper helper) {
        this.helper = helper;
    }

    public List<P1ItemDto> findByChecklistId(long checklistId) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql =
                "SELECT "
                        + ChecklistP1ItemDDL.COL_ID + ", "
                        + ChecklistP1ItemDDL.COL_CHECKLIST_ID + ", "
                        + ChecklistP1ItemDDL.COL_SECTION_NO + ", "
                        + ChecklistP1ItemDDL.COL_ITEM_NO + ", "

                        + ChecklistP1ItemDDL.COL_SEC_CATEGORY + ", "
                        + ChecklistP1ItemDDL.COL_SEC_MAIN_QUESTION + ", "
                        + ChecklistP1ItemDDL.COL_SEC_TARGET + ", "
                        + ChecklistP1ItemDDL.COL_SEC_DESCRIPTION + ", "
                        + ChecklistP1ItemDDL.COL_SEC_REFERENCE + ", "

                        + ChecklistP1ItemDDL.COL_EVIDENCE + ", "
                        + ChecklistP1ItemDDL.COL_DETAIL_DESC + ", "
                        + ChecklistP1ItemDDL.COL_GOOD_CASE + ", "
                        + ChecklistP1ItemDDL.COL_WEAK_CASE + ", "

                        + ChecklistP1ItemDDL.COL_RESULT + ", "
                        + ChecklistP1ItemDDL.COL_REMARK
                        + " FROM " + ChecklistP1ItemDDL.TABLE
                        + " WHERE " + ChecklistP1ItemDDL.COL_CHECKLIST_ID + " = ? "
                        + " ORDER BY " + ChecklistP1ItemDDL.COL_SECTION_NO + ", "
                        + ChecklistP1ItemDDL.COL_ITEM_NO;

        String[] args = { String.valueOf(checklistId) };

        List<P1ItemDto> result = new ArrayList<>();

        try (Cursor c = db.rawQuery(sql, args)) {
            while (c.moveToNext()) {
                int i = 0;
                P1ItemDto dto = P1ItemDto.create(
                        c.getLong(i++),
                        c.getLong(i++),
                        c.getInt(i++),
                        c.getInt(i++),
                        c.getString(i++),
                        c.getString(i++),
                        c.getString(i++),
                        c.getString(i++),
                        c.getString(i++),
                        c.getString(i++),
                        c.getString(i++),
                        c.getString(i++),
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
