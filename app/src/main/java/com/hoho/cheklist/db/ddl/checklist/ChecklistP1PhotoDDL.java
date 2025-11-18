package com.hoho.cheklist.db.ddl.checklist;

/**
 * 1단계 항목별 사진 테이블 DDL
 * - 각 P1 하위 항목(1-1, 1-2, 1-3...)당 최대 4장(제한은 앱 로직에서) 저장
 */
public final class ChecklistP1PhotoDDL {

    public static final String TABLE = "checklist_p1_photo";       // 테이블 이름
    public static final String COL_ID = "id";                      // PK
    public static final String COL_P1_ITEM_ID = "p1_item_id";      // FK → checklist_p1_item.id
    public static final String COL_PHOTO_PATH = "photo_path";      // 사진 파일 경로

    private ChecklistP1PhotoDDL() {}

    public static String create() {
        return "CREATE TABLE " + TABLE + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_P1_ITEM_ID + " INTEGER NOT NULL, "
                + COL_PHOTO_PATH + " TEXT NOT NULL, "
                + "FOREIGN KEY(" + COL_P1_ITEM_ID + ") REFERENCES "
                + ChecklistP1ItemDDL.TABLE + "(" + ChecklistP1ItemDDL.COL_ID + ") "
                + "ON DELETE CASCADE"
                + ");";
    }
}

