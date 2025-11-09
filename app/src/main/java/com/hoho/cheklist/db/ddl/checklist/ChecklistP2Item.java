package com.hoho.cheklist.db.ddl.checklist;

/**
 * 2단계 점검 결과 테이블 DDL
 * - 2단계 10개 항목에 대한 결과/비고/문구 스냅샷 저장
 */
public final class ChecklistP2Item {

    public static final String TABLE = "checklist_p2_item";        // 테이블 이름
    public static final String COL_ID = "id";                      // PK
    public static final String COL_CHECKLIST_ID = "checklist_id";  // FK → checklist.id
    public static final String COL_ITEM_NO = "item_no";            // 항목 번호(1~10)
    public static final String COL_LABEL = "label";                // 당시 문구 스냅샷
    public static final String COL_RESULT = "result";              // 결과('GOOD','BAD','ETC')
    public static final String COL_REMARK = "remark";              // 비고

    private ChecklistP2Item() {}

    public static String create() {
        return "CREATE TABLE " + TABLE + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_CHECKLIST_ID + " INTEGER NOT NULL, "
                + COL_ITEM_NO + " INTEGER NOT NULL, "
                + COL_LABEL + " TEXT NOT NULL, "
                + COL_RESULT + " TEXT, "
                + COL_REMARK + " TEXT, "
                + "UNIQUE(" + COL_CHECKLIST_ID + ", " + COL_ITEM_NO + "), "
                + "FOREIGN KEY(" + COL_CHECKLIST_ID + ") REFERENCES "
                + Checklist.TABLE + "(" + Checklist.COL_ID + ") "
                + "ON DELETE CASCADE"
                + ");";
    }
}

