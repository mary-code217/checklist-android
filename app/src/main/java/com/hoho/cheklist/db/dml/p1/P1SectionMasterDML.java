package com.hoho.cheklist.db.dml.p1;

import android.database.sqlite.SQLiteDatabase;

public final class P1SectionMasterDML {

    private P1SectionMasterDML() {}

    public static void insertSections(SQLiteDatabase db) {
        db.execSQL(
                "INSERT INTO p1_section_master (" +
                        "section_no, category, main_question, target, description, reference_basis, use_yn, sort_order" +
                        ") VALUES (" +
                        "1, " +
                        "'관리', " +
                        "'제어시스템 및 관련 장비 도입 시 보안 요구사항에 따른 검증을 실시 하였는가?', " +
                        "'디지털변전소(SA) 제어시스템', " +
                        "'제어시스템 도입 시 보안성 검토 등 관련 절차 준수 여부 확인', " +
                        "'국정원 보호대책 이행여부 No.20 / 분기 보안점검 체크리스트 No.7', " +
                        "1, " +
                        "1" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_section_master (" +
                        "section_no, category, main_question, target, description, reference_basis, use_yn, sort_order" +
                        ") VALUES (" +
                        "2, " +
                        "'관리', " +
                        "'정보시스템 자산에 대해 최신 현황을 유지하고 있는가?', " +
                        "'디지털변전소(SA) 제어시스템', " +
                        "'정보시스템 자산 관리 적합도 확인', " +
                        "'국정원 보호대책 이행여부 No.19 / 분기 보안점검 체크리스트 No.8', " +
                        "1, " +
                        "2" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_section_master (" +
                        "section_no, category, main_question, target, description, reference_basis, use_yn, sort_order" +
                        ") VALUES (" +
                        "3, " +
                        "'관리', " +
                        "'중요자료는 대외비 이상으로 생산하여 독립PC 또는 보안USB 보관, 암호화 등 안전하게 관리하고 있는가?', " +
                        "'디지털변전소(SA) 제어시스템', " +
                        "'중요자료 생산 및 관리 관련 보안 안전성 확보', " +
                        "'국정원 No.9 / 분기 보안점검 체크리스트 No.9', " +
                        "1, " +
                        "3" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_section_master (" +
                        "section_no, category, main_question, target, description, reference_basis, use_yn, sort_order" +
                        ") VALUES (" +
                        "4, " +
                        "'관리', " +
                        "'취약점 분석·평가 및 보호대책 이행여부 확인 결과 발견된 문제점을 보완조치 하였는가?', " +
                        "'디지털변전소(SA) 제어시스템', " +
                        "'취약점 분석·평가 시 발생한 문제점을 보완조치 하여 기반시설 보안성 제고', " +
                        "'국정원 보호대책 이행여부 No.24 / 분기 보안점검 체크리스트 No.24', " +
                        "1, " +
                        "4" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_section_master (" +
                        "section_no, category, main_question, target, description, reference_basis, use_yn, sort_order" +
                        ") VALUES (" +
                        "5, " +
                        "'관리', " +
                        "'백신 프로그램을 설치할 수 없는 단말기에 대해 프로세스 점검, 비인가 기기 접속여부 등을 점검하였는가?', " +
                        "'디지털변전소(SA) 제어시스템', " +
                        "'백신 프로그램 설치가 불가할 경우 별도 보안대책을 수립하여 기반시설 보호', " +
                        "'국정원 보호대책 이행여부 No.27 / 분기 보안점검 체크리스트 No.16', " +
                        "1, " +
                        "5" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_section_master (" +
                        "section_no, category, main_question, target, description, reference_basis, use_yn, sort_order" +
                        ") VALUES (" +
                        "6, " +
                        "'관리', " +
                        "'사이버위기를 가정한 대응 모의훈련을 실시하고, 비상대비체계의 정상작동 여부를 점검하였는가?', " +
                        "'디지털변전소(SA) 제어시스템', " +
                        "'모의훈련 실시, 비상대비체계의 점검을 통한 침해사고 발생 시 즉시 대응체계 구축', " +
                        "'국정원 보호대책 이행여부 No.31 / 분기 보안점검 체크리스트 No.25', " +
                        "1, " +
                        "6" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_section_master (" +
                        "section_no, category, main_question, target, description, reference_basis, use_yn, sort_order" +
                        ") VALUES (" +
                        "7, " +
                        "'관리', " +
                        "'PC·서버의 운영체제 등 S/W에 대해 최신 보안패치를 하였는가?', " +
                        "'디지털변전소(SA) 제어시스템', " +
                        "'OS, S/W의 주기적인 취약점 수정 패치를 통한 기반시설 보호', " +
                        "'국정원 보호대책 이행여부 No.13 / 분기 보안점검 체크리스트 No.17', " +
                        "1, " +
                        "7" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_section_master (" +
                        "section_no, category, main_question, target, description, reference_basis, use_yn, sort_order" +
                        ") VALUES (" +
                        "8, " +
                        "'물리', " +
                        "'연결포트 잠금장치·보안스티커 등을 사용하여 물리적 봉인하고, 봉인장치 개폐시 사용내역을 기록, 유지하고 있는가?', " +
                        "'디지털변전소(SA) 제어시스템', " +
                        "'물리 연결 포트(LAN, USB, 광)의 봉인장치 체결, 봉인스티커 부착을 통한 무단연결 차단', " +
                        "'국정원 보호대책 이행여부 No.12 / 분기 보안점검 체크리스트 No.14', " +
                        "1, " +
                        "8" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_section_master (" +
                        "section_no, category, main_question, target, description, reference_basis, use_yn, sort_order" +
                        ") VALUES (" +
                        "9, " +
                        "'물리', " +
                        "'휴대용 기기에 대한 반·출입 통제를 하고 있는가?', " +
                        "'디지털변전소(SA) 제어시스템', " +
                        "'기반시설 출입시 휴대용 기기 반입을 통제하여 외부로의 무단 기밀 반출을 통제함', " +
                        "'국정원 보호대책 이행여부 No.21-1 / 분기 보안점검 체크리스트 No.22', " +
                        "1, " +
                        "9" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_section_master (" +
                        "section_no, category, main_question, target, description, reference_basis, use_yn, sort_order" +
                        ") VALUES (" +
                        "10, " +
                        "'물리', " +
                        "'외부에서 반입·사용하는 정보통신기기에 대해 포맷 후 필요한 프로그램만 설치 등 보안조치 하였는가?', " +
                        "'디지털변전소(SA) 제어시스템', " +
                        "'외부에서 반입하는 정보통신기기를 통제하여 기반시설망을 보호', " +
                        "'산업부 취약점 분석 평가 A-57 / 분기 보안점검 체크리스트 No.23', " +
                        "1, " +
                        "10" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_section_master (" +
                        "section_no, category, main_question, target, description, reference_basis, use_yn, sort_order" +
                        ") VALUES (" +
                        "11, " +
                        "'물리', " +
                        "'비인가 PC·서버 및 네트워크의 접속을 차단하였는가?', " +
                        "'디지털변전소(SA) 제어시스템', " +
                        "'대상 설비들의 물리 포트(LAN, USB 등) 접근 통제 및 비인가자 접근 차단', " +
                        "'국정원 보호대책 이행여부 No.11 / 분기 보안점검 체크리스트 No.2', " +
                        "1, " +
                        "11" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_section_master (" +
                        "section_no, category, main_question, target, description, reference_basis, use_yn, sort_order" +
                        ") VALUES (" +
                        "12, " +
                        "'물리', " +
                        "'제어망에서 내부망으로 자료전송을 위한 일방향 전송장비를 운용하는가?', " +
                        "'디지털변전소(SA) 제어시스템', " +
                        "'일방향 전송장비 운영을 통한 망간 데이터 이동 통제', " +
                        "'국정원 보호대책 이행여부 No.8 / 분기 보안점검 체크리스트 No.3', " +
                        "1, " +
                        "12" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_section_master (" +
                        "section_no, category, main_question, target, description, reference_basis, use_yn, sort_order" +
                        ") VALUES (" +
                        "13, " +
                        "'물리', " +
                        "'비인가 무선통신(예 : Wi-Fi) 사용여부를 점검하고 통제하고 있는가? (WIPS 설치 및 운영)', " +
                        "'디지털변전소(SA) 제어시스템', " +
                        "'무선 침입 방지 체계를 통한 무선 침해사고로부터 기반시설 보호', " +
                        "'산업부 취약점 분석 평가 A-22, 23 / 분기 보안점검 체크리스트 No.4', " +
                        "1, " +
                        "13" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_section_master (" +
                        "section_no, category, main_question, target, description, reference_basis, use_yn, sort_order" +
                        ") VALUES (" +
                        "14, " +
                        "'물리', " +
                        "'자료 전송 시 광디스크를 사용하거나 지정된 전용 단말기를 사용하고 있는가? (CD 지정단말)', " +
                        "'디지털변전소(SA) 제어시스템', " +
                        "'일회성 저장매체(CD) 및 전용 지정 단말기 사용을 통한 자료의 반·출입 통제', " +
                        "'산업부 취약점 분석 평가 PC-13/ 분기 보안점검 체크리스트 No.18', " +
                        "1, " +
                        "14" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_section_master (" +
                        "section_no, category, main_question, target, description, reference_basis, use_yn, sort_order" +
                        ") VALUES (" +
                        "15, " +
                        "'물리', " +
                        "'‘클린PC’ 사용 시 백신 검사, 특정 사이트로 접속 제한 등 보안조치를 하였는가?', " +
                        "'디지털변전소(SA) 제어시스템', " +
                        "'‘클린PC’의 적절한 보안관리를 통한 안전한 일회성 저장매체 반·출입 및 생산환경 구축', " +
                        "'산업부 취약점 분석 평가 A-57 / 분기 보안점검 체크리스트 No.19', " +
                        "1, " +
                        "15" +
                        ");"
        );
    }
}
