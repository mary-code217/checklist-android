package com.hoho.cheklist.db.ddl.P1;

/**
 * 1단계 점검 대분류(섹션) 마스터 DDL
 *
 * 화면 상단 파란 영역에 보이는 "No / 구분 / 항목명 / 점검대상 / 설명 / 관련근거" 정의
 * 예)
 *  - section_no      : 1
 *  - category        : "관리"
 *  - main_question   : "제어시스템 및 관련 장비 도입 시 보안 요구사항에 따른 검증을 실시 하였는가?"
 *  - target          : "디지털변전소(SA) 제어시스템"
 *  - description     : "제어시스템 도입 시 보안성 검토 등 관련 절차 준수 여부 확인"
 *  - reference_basis : "국정원 보호대책 이행여부 No.20 / 분기 보안점검 체크리스트 No.7"
 */
public final class P1SectionMasterDDL {

    public static final String TABLE = "p1_section_master";          // 테이블 이름

    public static final String COL_ID = "id";                        // PK
    public static final String COL_SECTION_NO = "section_no";        // 섹션 번호(1~15)
    public static final String COL_CATEGORY = "category";            // 구분 (예: 관리, 물리)
    public static final String COL_MAIN_QUESTION = "main_question";  // 항목명(메인 질문 문구)
    public static final String COL_TARGET = "target";                // 점검대상
    public static final String COL_DESCRIPTION = "description";      // 설명
    public static final String COL_REFERENCE = "reference_basis";    // 관련근거

    public static final String COL_USE_YN = "use_yn";                // 사용 여부(1=사용,0=숨김)
    public static final String COL_SORT_ORDER = "sort_order";        // 표시 순서

    private P1SectionMasterDDL() {}

    public static String create() {
        return "CREATE TABLE " + TABLE + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_SECTION_NO + " INTEGER NOT NULL, "
                + COL_CATEGORY + " TEXT NOT NULL, "
                + COL_MAIN_QUESTION + " TEXT NOT NULL, "
                + COL_TARGET + " TEXT, "
                + COL_DESCRIPTION + " TEXT, "
                + COL_REFERENCE + " TEXT, "
                + COL_USE_YN + " INTEGER NOT NULL DEFAULT 1, "
                + COL_SORT_ORDER + " INTEGER NOT NULL, "
                + "UNIQUE(" + COL_SECTION_NO + ")"
                + ");";
    }
}
