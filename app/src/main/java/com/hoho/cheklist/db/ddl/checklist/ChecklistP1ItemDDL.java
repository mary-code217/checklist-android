package com.hoho.cheklist.db.ddl.checklist;

/**
 * 1단계 점검 결과 테이블 DDL
 * - 각 하위 항목(1-1,1-2...)에 대한 결과/비고/문구 스냅샷 저장
 */
public final class ChecklistP1ItemDDL {

    public static final String TABLE = "checklist_p1_item";        // 테이블 이름
    public static final String COL_ID = "id";                      // PK
    public static final String COL_CHECKLIST_ID = "checklist_id";  // FK → checklist.id
    public static final String COL_SECTION_NO = "section_no";      // 대항목 번호(1~15)
    public static final String COL_ITEM_NO = "item_no";            // 하위 번호(1,2,3.. → 1-1)
    public static final String COL_LABEL = "label";                // 당시 문구 스냅샷
    public static final String COL_RESULT = "result";              // 결과('GOOD','BAD','ETC' 또는 한글)
    public static final String COL_REMARK = "remark";              // 비고(기타 선택 시)

    private ChecklistP1ItemDDL() {}

    public static String create() {
        return "CREATE TABLE " + TABLE + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_CHECKLIST_ID + " INTEGER NOT NULL, "
                + COL_SECTION_NO + " INTEGER NOT NULL, "
                + COL_ITEM_NO + " INTEGER NOT NULL, "
                + COL_LABEL + " TEXT NOT NULL, "
                + COL_RESULT + " TEXT, "
                + COL_REMARK + " TEXT, "
                + "UNIQUE(" + COL_CHECKLIST_ID + ", " + COL_SECTION_NO + ", " + COL_ITEM_NO + "), "
                + "FOREIGN KEY(" + COL_CHECKLIST_ID + ") REFERENCES "
                + ChecklistDDL.TABLE + "(" + ChecklistDDL.COL_ID + ") "
                + "ON DELETE CASCADE"
                + ");";
    }
}
