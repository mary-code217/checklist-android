package com.hoho.cheklist.db.ddl.P1;

/**
 * 1단계 점검 소분류(하위 항목) 마스터 DDL
 * 상세 점검 항목(1-1, 1-2, ...)의 기본 정의.
 *
 * 예) 1-1 항목
 *  - evidence      : "제어시스템 도입 설비 목록"
 *  - detail_desc   : "인가된 제어시스템 설비를 목록화 하여 관리"
 *  - good_case     : "설비 목록 및 현장 운영 설비와 일치"
 *  - weak_case     : "미일치, 누락 등"
 *
 * 이 값들을 기반으로 실제 체크 화면에 노출하고,
 * 체크리스트 생성 시 스냅샷을 checklist_p1_item 쪽에 복사 저장.
 */
public final class P1ItemMaster {

    public static final String TABLE = "p1_item_master";             // 테이블 이름

    public static final String COL_ID = "id";                        // PK
    public static final String COL_SECTION_ID = "section_id";        // FK → p1_section_master.id
    public static final String COL_ITEM_NO = "item_no";              // 소분류 번호(1,2,3... → section_no와 조합해 1-1,1-2)

    public static final String COL_EVIDENCE = "evidence";            // 증빙
    public static final String COL_DETAIL_DESC = "detail_desc";      // 설명(상세 점검 내용)
    public static final String COL_GOOD_CASE = "good_case";          // 양호 기준
    public static final String COL_WEAK_CASE = "weak_case";          // 취약 사례

    public static final String COL_USE_YN = "use_yn";                // 사용 여부(1=사용,0=숨김)
    public static final String COL_SORT_ORDER = "sort_order";        // 표시 순서

    private P1ItemMaster() {}

    public static String create() {
        return "CREATE TABLE " + TABLE + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_SECTION_ID + " INTEGER NOT NULL, "
                + COL_ITEM_NO + " INTEGER NOT NULL, "
                + COL_EVIDENCE + " TEXT, "
                + COL_DETAIL_DESC + " TEXT, "
                + COL_GOOD_CASE + " TEXT, "
                + COL_WEAK_CASE + " TEXT, "
                + COL_USE_YN + " INTEGER NOT NULL DEFAULT 1, "
                + COL_SORT_ORDER + " INTEGER NOT NULL, "
                + "UNIQUE(" + COL_SECTION_ID + ", " + COL_ITEM_NO + "), "
                + "FOREIGN KEY(" + COL_SECTION_ID + ") REFERENCES "
                + P1SectionMaster.TABLE + "(" + P1SectionMaster.COL_ID + ") "
                + "ON DELETE CASCADE"
                + ");";
    }
}