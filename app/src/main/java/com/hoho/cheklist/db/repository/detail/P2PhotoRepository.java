package com.hoho.cheklist.db.repository.detail;

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


}
