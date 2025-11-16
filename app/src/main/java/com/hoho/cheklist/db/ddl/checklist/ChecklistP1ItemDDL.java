package com.hoho.cheklist.db.ddl.checklist;

/**
 * 1단계(P1) 점검 결과 + 문구 스냅샷 테이블
 * - 각 하위 항목(1-1,1-2...)에 대한
 *   대항목/하위항목 문구 전체 + 결과/비고 저장
 */
public final class ChecklistP1ItemDDL {

    public static final String TABLE = "checklist_p1_item";        // 테이블 이름

    public static final String COL_ID = "id";                      // PK
    public static final String COL_CHECKLIST_ID = "checklist_id";  // FK → checklist.id

    // 위치 정보
    public static final String COL_SECTION_NO = "section_no";      // 대항목 번호(1~15)
    public static final String COL_ITEM_NO = "item_no";            // 하위 번호(1,2,3.. → 1-1)

    // ▼▼ 당시 대항목(섹션) 스냅샷 ▼▼
    public static final String COL_SEC_CATEGORY      = "sec_category";       // 구분
    public static final String COL_SEC_MAIN_QUESTION = "sec_main_question";  // 항목명(메인 질문)
    public static final String COL_SEC_TARGET        = "sec_target";         // 점검대상
    public static final String COL_SEC_DESCRIPTION   = "sec_description";    // 설명
    public static final String COL_SEC_REFERENCE     = "sec_reference";      // 관련근거

    // ▼▼ 당시 하위항목 스냅샷 ▼▼
    public static final String COL_EVIDENCE    = "evidence";       // 증빙
    public static final String COL_DETAIL_DESC = "detail_desc";    // 설명(상세 점검 내용)
    public static final String COL_GOOD_CASE   = "good_case";      // 양호 기준
    public static final String COL_WEAK_CASE   = "weak_case";      // 취약 사례

    // ▼▼ 실제 점검 결과 ▼▼
    public static final String COL_RESULT = "result";              // 결과('양호','미흡','기타')
    public static final String COL_REMARK = "remark";              // 비고(기타 선택 시)

    private ChecklistP1ItemDDL() {}

    public static String create() {
        return "CREATE TABLE " + TABLE + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_CHECKLIST_ID + " INTEGER NOT NULL, "
                + COL_SECTION_NO + " INTEGER NOT NULL, "
                + COL_ITEM_NO + " INTEGER NOT NULL, "

                + COL_SEC_CATEGORY + " TEXT, "
                + COL_SEC_MAIN_QUESTION + " TEXT, "
                + COL_SEC_TARGET + " TEXT, "
                + COL_SEC_DESCRIPTION + " TEXT, "
                + COL_SEC_REFERENCE + " TEXT, "

                + COL_EVIDENCE + " TEXT, "
                + COL_DETAIL_DESC + " TEXT, "
                + COL_GOOD_CASE + " TEXT, "
                + COL_WEAK_CASE + " TEXT, "

                + COL_RESULT + " TEXT, "
                + COL_REMARK + " TEXT, "

                + "UNIQUE(" + COL_CHECKLIST_ID + ", " + COL_SECTION_NO + ", " + COL_ITEM_NO + "), "
                + "FOREIGN KEY(" + COL_CHECKLIST_ID + ") REFERENCES "
                + ChecklistDDL.TABLE + "(" + ChecklistDDL.COL_ID + ") "
                + "ON DELETE CASCADE"
                + ");";
    }
}
