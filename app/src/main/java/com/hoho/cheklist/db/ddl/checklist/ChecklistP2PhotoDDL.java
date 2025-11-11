package com.hoho.cheklist.db.ddl.checklist;

/**
 * 2단계 공통 사진 테이블 DDL
 * - 2단계 10개 항목 전체에 대한 공통 사진(최대 4장, 제한은 앱 로직) 저장
 */
public final class ChecklistP2PhotoDDL {

    public static final String TABLE = "checklist_p2_photo";       // 테이블 이름
    public static final String COL_ID = "id";                      // PK
    public static final String COL_CHECKLIST_ID = "checklist_id";  // FK → checklist.id
    public static final String COL_PHOTO_PATH = "photo_path";      // 사진 파일 경로

    private ChecklistP2PhotoDDL() {}

    public static String create() {
        return "CREATE TABLE " + TABLE + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_CHECKLIST_ID + " INTEGER NOT NULL, "
                + COL_PHOTO_PATH + " TEXT NOT NULL, "
                + "FOREIGN KEY(" + COL_CHECKLIST_ID + ") REFERENCES "
                + ChecklistDDL.TABLE + "(" + ChecklistDDL.COL_ID + ") "
                + "ON DELETE CASCADE"
                + ");";
    }
}

