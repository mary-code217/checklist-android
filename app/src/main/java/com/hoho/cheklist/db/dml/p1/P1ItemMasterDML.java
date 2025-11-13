package com.hoho.cheklist.db.dml.p1;

import android.database.sqlite.SQLiteDatabase;

public final class P1ItemMasterDML {

    private P1ItemMasterDML() {}

    public static void  insertItem(SQLiteDatabase db) {
        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "1, " +
                        "1, " +
                        "'제어시스템 도입 설비 목록', " +
                        "'인가된 제어시스템 설비를 목록화 하여 관리', " +
                        "'설비 목록 및 현장 운영 설비와 일치', " +
                        "'미일치, 누락 등', " +
                        "1, " +
                        "1" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "1, " +
                        "2, " +
                        "'보안성 검토 요청서', " +
                        "'설비 도입전 보안성 검토 요청을 하여 관련 보안조치를 시행함', " +
                        "'보안성 검토 요청서 존재', " +
                        "'미작성, 누락 등', " +
                        "1, " +
                        "2" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "1, " +
                        "3, " +
                        "'보안성 점검 이행', " +
                        "'보안성 검토 완료 및 도입 후 보안조치를 시행하여 취약점을 제거', " +
                        "'보안취약점 제거조치 점검표 존재', " +
                        "'미시행, 점검표 미작성', " +
                        "1, " +
                        "3" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "2, " +
                        "1, " +
                        "'자산관리대장', " +
                        "'자산관리대장을 통한 기반시설의 도입 이력 관리', " +
                        "'자산관리대장 최신화', " +
                        "'누락, 미작성 등', " +
                        "1, " +
                        "4" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "3, " +
                        "1, " +
                        "'(해당할 경우) 비밀 관리대장', " +
                        "'중요자료(대외비 이상)의 관리체계 수립을 통한 안전한 관리환경 구축', " +
                        "'생산된 자료와 대장 내역 일치 및 최신화', " +
                        "'누락, 미작성 등', " +
                        "1, " +
                        "5" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "3, " +
                        "2, " +
                        "'계정관리대장', " +
                        "'부득이하게 설치가 불가할 경우 사유 및 관련 근거 확보', " +
                        "'증빙자료 존재', " +
                        "'증빙 불가', " +
                        "1, " +
                        "6" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "4, " +
                        "1, " +
                        "'취약점 분석·평가 후속조치 결과 보고서', " +
                        "'발생된 보안 취약점을 조치하여 기반시설 보안성 제고', " +
                        "'후속조치 결과 보고서 존재', " +
                        "'후속조치 미시행, 보고서 누락', " +
                        "1, " +
                        "7" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "4, " +
                        "2, " +
                        "'(해당할 경우) 취약점 조치 불가 관련 증빙', " +
                        "'발견한 취약점의 조치가 불가능할 경우 관련 증빙을 통한 별도 관리 시행', " +
                        "'제조사 의견서, 조치 불가 관련 증빙 존재', " +
                        "'증빙 없음', " +
                        "1, " +
                        "8" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "5, " +
                        "1, " +
                        "'봉인스티커 관리대장', " +
                        "'봉인스티커의 부착, 개봉 이력을 관리하여 물리 포트 연결 이력 관리', " +
                        "'관리대장을 최신화 하여 성실히 작성하였음', " +
                        "'개폐일자 ↔ 작업일자 미일치, 미부착, 항목누락 등', " +
                        "1, " +
                        "9" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "5, " +
                        "2, " +
                        "'백신 설치불가 관련 증빙', " +
                        "'부득이하게 설치가 불가할 경우 사유 및 관련 근거 확보', " +
                        "'증빙자료 존재', " +
                        "'증빙 불가', " +
                        "1, " +
                        "10" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "5, " +
                        "3, " +
                        "'(해당할 경우) 터보백신 사용 관리대장', " +
                        "'Portable 방식 백신을 사용하여 별도로 시스템 검사 실시', " +
                        "'터보백신 엔진 최신화, 월 1회 점검 실시 내역 존재', " +
                        "'미작성, 미비치, 항목누락 등', " +
                        "1, " +
                        "11" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "6, " +
                        "1, " +
                        "'장애대응, 시스템 복구 훈련 내역', " +
                        "'주기적 훈련을 통한 침해사고 발생 시 대응력 확보', " +
                        "'최신 훈련 내역 존재', " +
                        "'미실시, 누락', " +
                        "1, " +
                        "12" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "6, " +
                        "2, " +
                        "'주요기반시설 업무연속성 확보 대책', " +
                        "'업무연속성 확보 대책을 통한 장애, 사고 발생 시 즉시 대응체계 구축', " +
                        "'업무연속성 확보 대책 최신화', " +
                        "'최신화 미시행, 누락', " +
                        "1, " +
                        "13" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "7, " +
                        "1, " +
                        "'OS 업데이트 이력', " +
                        "'Windows OS 최신 빌드 설치, 주기적 보안 패치를 통한 취약점 제거', " +
                        "'Windows 최신버전 및 주기적 Hotfix를 업데이트 하여 운용함', " +
                        "'업데이트 미실시, 누락 등', " +
                        "1, " +
                        "14" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "7, " +
                        "2, " +
                        "'주요 S/W 보안 업데이트 이력', " +
                        "'Windows 주요 S/W(Edge, Microsoft Office)의 주기적 보안 업데이트 여부', " +
                        "'Windows 주요 S/W 최신버전 운용', " +
                        "'업데이트 미실시, 누락 등', " +
                        "1, " +
                        "15" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "7, " +
                        "3, " +
                        "'사용프로그램 관리대장', " +
                        "'허가를 득한 사용 프로그램들을 관리하여 기반시설을 보호', " +
                        "'대상 설비의 사용중인 프로그램과 관리대장 내역이 일치함', " +
                        "'미일치, 누락 등', " +
                        "1, " +
                        "16" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "8, " +
                        "1, " +
                        "'봉인스티커 관리대장', " +
                        "'봉인스티커의 부착, 개봉 이력을 관리하여 물리 포트 연결 이력 관리', " +
                        "'관리대장을 최신화 하여 성실히 작성하였음', " +
                        "'개폐일자 ↔ 작업일자 미일치, 미부착, 항목누락 등', " +
                        "1, " +
                        "17" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "8, " +
                        "2, " +
                        "'포트락 체결 여부', " +
                        "'물리 포트(LAN, USB) 포트락을 체결하여 물리적 네트워크 연결 차단', " +
                        "'모든 포트에 포트락을 성실하게 체결했음', " +
                        "'누락, 미체결', " +
                        "1, " +
                        "18" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "9, " +
                        "1, " +
                        "'휴대용 기기 보관함, 금속탐지기', " +
                        "'기반시설 출입 시 출입자는 휴대용 기기를 별도 장소에 보관하여 출입', " +
                        "'보관함 및 금속탐지기를 사용하여 출입자의 휴대용 기기 반출입 관리', " +
                        "'보관함, 금속탐지기 미설치, 작동 불가 등', " +
                        "1, " +
                        "19" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "9, " +
                        "2, " +
                        "'휴대용 기기 관리대장', " +
                        "'휴대용 기기 관리대장을 통한 출입자의 휴대용기기 반출입 여부 관리', " +
                        "'휴대용 기기 관리대장을 성실하게 작성하며 관리함', " +
                        "'내용누락, 최신화 미비 등', " +
                        "1, " +
                        "20" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "10, " +
                        "1, " +
                        "'정보통신기기 반·출입대장', " +
                        "'유지보수용 노트북 등의 정보통신기기 반·출입 내역을 기록하여 이력 관리', " +
                        "'정보통신기기 반·출입대장을 최신화 하여 성실히 작성', " +
                        "'미작성, 항목 누락 등', " +
                        "1, " +
                        "21" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "10, " +
                        "2, " +
                        "'정보통신기기 점검대장(백신검사, 불필요 자료, 프로그램 확인, 네트워크 차단 등)', " +
                        "'외부 정보통신기기 반·출입 시 적절한 보안 조치 후 사용', " +
                        "'정보통신기기 점검대장을 최신화 하여 성실히 작성', " +
                        "'미작성, 항목 누락 등', " +
                        "1, " +
                        "22" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "11, " +
                        "1, " +
                        "'포트락 체결 여부', " +
                        "'물리 포트(LAN, USB) 포트락을 체결하여 물리적 네트워크 연결 차단', " +
                        "'모든 포트에 포트락을 성실하게 체결했음', " +
                        "'누락, 미체결', " +
                        "1, " +
                        "23" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "11, " +
                        "2, " +
                        "'전용함체 및 잠금장치 여부', " +
                        "'설비 전용 함체 및 잠금장치를 사용하여 비인가자의 물리적 접근 차단 ', " +  // 원문 공백 유지
                        "'전용 함체를 사용하며 잠금장치를 통한 비인가자의 접근을 차단함', " +
                        "'설비 노출, 잠금 장치 없음 등', " +
                        "1, " +
                        "24" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "12, " +
                        "1, " +
                        "'(해당할 경우) 일방향 전송장비 운용 여부', " +
                        "'일방향 전송장비 운영을 통한 망간 데이터 이동 통제', " +
                        "'일방향 전송장비의 보안조치를 했으며 정상적으로 운용함', " +
                        "'일방향 전송장비 보안조치 미시행', " +
                        "1, " +
                        "25" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "13, " +
                        "1, " +
                        "'WIPS 운영 여부', " +
                        "'무선 침입 차단 시스템 운영을 통한 비인가 무선 침입 차단', " +
                        "'WIPS 설치 및 정상적으로 운영하고 있음', " +
                        "'WIPS 미설치 또는 정상 운영 불가(고장 등)', " +
                        "1, " +
                        "26" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "13, " +
                        "2, " +
                        "'Wi-Fi SSID 검색 내역', " +
                        "'WIPS 범위 밖, 사각지대의 Wi-Fi 신호 검색을 통한 비인가 무선 침입 차단', " +
                        "'WIFI 검색 결과 무선 SSID가 검색되지 않음', " +
                        "'WIFI 검색 결과 무선 SSID가 검색됨', " +
                        "1, " +
                        "27" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "14, " +
                        "1, " +
                        "'(해당하는 경우) CD 지정 단말기', " +
                        "'지정된 단말을 이용하여 일회성 저장매체(CD) 자료 반·출입 통제 여부', " +
                        "'CD 지정 단말기의 보안조치, 관리대장을 성실하게 관리했음', " +
                        "'CD 지정 단말기 보안조치 미비, 관리대장 내용 누락 등', " +
                        "1, " +
                        "28" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "14, " +
                        "2, " +
                        "'휴대용 저장매체 관리대장', " +
                        "'일회성 저장매체(CD)의 반입, 반출 및 생성, 파기 여부 적절성 확인', " +
                        "'휴대용 저장매체 관리대장 최신화 및 관리를 성실하게 했음', " +
                        "'휴대용 저장매체 관리대장 미비치, 내용누락 등 관리미비', " +
                        "1, " +
                        "29" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "15, " +
                        "1, " +
                        "'클린PC 백신, 보안프로그램 가동', " +
                        "'클린PC의 기술적 보안조치를 적용하여 안전하게 관리', " +
                        "'백신 등 보안프로그램 구동여부 또는 별도 클라우드 전용단말기 사용', " +
                        "'보안프로그램 미구동, 점검 미시행', " +
                        "1, " +
                        "30" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p1_item_master (" +
                        "section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order" +
                        ") VALUES (" +
                        "15, " +
                        "2, " +
                        "'클린PC 관리대장', " +
                        "'클린PC 저장매체, 용도, 관리번호, 사용자, 관리자 등의 정보가 기록', " +
                        "'매번 클린PC 사용 시 관련 내역을 성실하게 작성했음', " +
                        "'내용 누락, 미작성', " +
                        "1, " +
                        "31" +
                        ");"
        );
    }
}
