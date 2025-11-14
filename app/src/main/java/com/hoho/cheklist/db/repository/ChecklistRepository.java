package com.hoho.cheklist.db.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hoho.cheklist.db.AppDBHelper;
import com.hoho.cheklist.db.ddl.checklist.ChecklistDDL;
import com.hoho.cheklist.dto.checklist.ChecklistEntity;

import java.util.ArrayList;
import java.util.List;

public class ChecklistRepository {

    private final AppDBHelper helper;

    public ChecklistRepository(AppDBHelper helper) {
        this.helper = helper;
    }

    // 토탈 엘리먼트 가져오기
    public long countAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        // 1) 전체 행 개수 세는 SQL
        String sql = "SELECT COUNT(*) FROM checklist";

        long count = 0L;
        // 2) rawQuery로 실행하고, try-with-resources로 Cursor 자동 close
        try (Cursor c = db.rawQuery(sql, null)) {
            // 3) 결과가 한 줄이라 moveToFirst() 한 번만 체크
            if (c.moveToFirst()) {
                count = c.getLong(0); // 첫 번째 컬럼(COUNT(*)) 값을 long으로 읽기
            }
        }
        // 4) 읽어온 개수 반환
        return count;
    }

    // 페이징 처리해서 체크리스트 목록 가져오기
    public List<ChecklistEntity> findPage(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        SQLiteDatabase db = helper.getReadableDatabase();

        // 1) 조회할 컬럼들 + ORDER BY + LIMIT/OFFSET
        String sql =
                "SELECT " +
                        ChecklistDDL.COL_ID + ", " +
                        ChecklistDDL.COL_TITLE + ", " +
                        ChecklistDDL.COL_INSPECTED_AT + ", " +
                        ChecklistDDL.COL_DEPARTMENT + ", " +
                        ChecklistDDL.COL_SYSTEM_NAME + ", " +
                        ChecklistDDL.COL_INSPECTOR + ", " +
                        ChecklistDDL.COL_STAGE + ", " +
                        ChecklistDDL.COL_CREATED_AT + ", " +
                        ChecklistDDL.COL_UPDATED_AT +
                        " FROM " + ChecklistDDL.TABLE +
                        " ORDER BY " + ChecklistDDL.COL_CREATED_AT + " DESC, " + ChecklistDDL.COL_ID + " DESC " +
                        " LIMIT ? OFFSET ?";

        String[] args = {
                String.valueOf(pageSize),
                String.valueOf(offset)
        };

        List<ChecklistEntity> result = new ArrayList<>();

        // 2) rawQuery 실행 + Cursor를 Entity로 매핑
        try(Cursor c = db.rawQuery(sql, args)) {
            // 컬럼 인덱스는 루프 밖에서 한 번만 계산
            int idxId = c.getColumnIndexOrThrow(ChecklistDDL.COL_ID);
            int idxTitle = c.getColumnIndexOrThrow(ChecklistDDL.COL_TITLE);
            int idxInspectedAt = c.getColumnIndexOrThrow(ChecklistDDL.COL_INSPECTED_AT);
            int idxDepartment = c.getColumnIndexOrThrow(ChecklistDDL.COL_DEPARTMENT);
            int idxSystemName = c.getColumnIndexOrThrow(ChecklistDDL.COL_SYSTEM_NAME);
            int idxInspector = c.getColumnIndexOrThrow(ChecklistDDL.COL_INSPECTOR);
            int idxStage = c.getColumnIndexOrThrow(ChecklistDDL.COL_STAGE);
            int idxCreatedAt = c.getColumnIndexOrThrow(ChecklistDDL.COL_CREATED_AT);
            int idxUpdatedAt = c.getColumnIndexOrThrow(ChecklistDDL.COL_UPDATED_AT);

            while (c.moveToNext()) {
                long id              = c.getLong(idxId);
                String title         = c.getString(idxTitle);
                String inspectedAt   = c.getString(idxInspectedAt);
                String department    = c.getString(idxDepartment);
                String systemName    = c.getString(idxSystemName);
                String inspector     = c.getString(idxInspector);
                int stage            = c.getInt(idxStage);
                String createdAt     = c.getString(idxCreatedAt);
                String updatedAt     = c.getString(idxUpdatedAt);

                ChecklistEntity entity = ChecklistEntity.create(
                        id, title, inspectedAt, department,
                        systemName, inspector, stage, createdAt, updatedAt
                );

                result.add(entity);
            }
        }

        return result;
    }

    // 받은 ID 전부 삭제
    public int deleteByIds(List<Long> ids) {
        // 1) null 또는 빈 리스트면 바로 0 리턴
        if (ids == null || ids.isEmpty()) return 0;

        SQLiteDatabase db = helper.getWritableDatabase();

        // 2) where 절: id IN (?, ?, ?, ...)
        StringBuilder whereBuilder = new StringBuilder();
        whereBuilder.append(ChecklistDDL.COL_ID).append(" IN (");
        for (int i = 0; i < ids.size(); i++) {
            if (i > 0) {
                whereBuilder.append(", ");
            }
            whereBuilder.append("?");
        }
        whereBuilder.append(")");
        String whereClause = whereBuilder.toString();

        // 3) whereArgs 채우기
        String[] whereArgs = new String[ids.size()];
        for (int i = 0; i < ids.size(); i++) {
            whereArgs[i] = String.valueOf(ids.get(i));
        }

        // 4) delete() 호출 → 삭제된 row 개수 반환
        int deletedCount = db.delete(ChecklistDDL.TABLE, whereClause, whereArgs);

        return deletedCount;
    }
}
