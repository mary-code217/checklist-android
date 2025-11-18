package com.hoho.cheklist.db.ddl.checklist;

/**
 * 체크리스트 헤더 테이블 DDL
 * - 한 번의 점검(1단계+2단계)을 대표하는 상단 정보
 */
public final class ChecklistDDL {

    public static final String TABLE = "checklist";                // 테이블 이름
    public static final String COL_ID = "id";                      // PK
    public static final String COL_TITLE = "title";                // 체크리스트 제목
    public static final String COL_INSPECTED_AT = "inspected_at";  // 점검일시(문자열)
    public static final String COL_DEPARTMENT = "department";      // 관리부서
    public static final String COL_SYSTEM_NAME = "system_name";    // 점검시스템
    public static final String COL_INSPECTOR = "inspector";        // 점검자
    public static final String COL_STAGE = "stage";                // 진행단계(0:진행중,1:1단계완료,2:2단계완료,3:최종완료)
    public static final String COL_CREATED_AT = "created_at";      // 생성일시
    public static final String COL_UPDATED_AT = "updated_at";      // 수정일시

    private ChecklistDDL() {}

    public static String create() {
        return "CREATE TABLE " + TABLE + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE + " TEXT NOT NULL, "
                + COL_INSPECTED_AT + " TEXT, "
                + COL_DEPARTMENT + " TEXT, "
                + COL_SYSTEM_NAME + " TEXT, "
                + COL_INSPECTOR + " TEXT, "
                + COL_STAGE + " INTEGER NOT NULL DEFAULT 0, "
                + COL_CREATED_AT + " TEXT NOT NULL, "
                + COL_UPDATED_AT + " TEXT NOT NULL"
                + ");";
    }
}

