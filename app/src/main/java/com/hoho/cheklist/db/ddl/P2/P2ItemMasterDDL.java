package com.hoho.cheklist.db.ddl.P2;

/**
 * 2단계 점검 항목 마스터 DDL
 * - 추가 10개 항목 정의
 */
public final class P2ItemMasterDDL {

    public static final String TABLE = "p2_item_master";           // 테이블 이름
    public static final String COL_ID = "id";                      // PK
    public static final String COL_ITEM_NO = "item_no";            // 항목 번호(1~10)
    public static final String COL_LABEL = "label";                // 점검 문구
    public static final String COL_USE_YN = "use_yn";              // 사용 여부(1=사용,0=숨김)
    public static final String COL_SORT_ORDER = "sort_order";      // 표시 순서

    private P2ItemMasterDDL() {}

    public static String create() {
        return "CREATE TABLE " + TABLE + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ITEM_NO + " INTEGER NOT NULL, "
                + COL_LABEL + " TEXT NOT NULL, "
                + COL_USE_YN + " INTEGER NOT NULL DEFAULT 1, "
                + COL_SORT_ORDER + " INTEGER NOT NULL, "
                + "UNIQUE(" + COL_ITEM_NO + ")"
                + ");";
    }
}
