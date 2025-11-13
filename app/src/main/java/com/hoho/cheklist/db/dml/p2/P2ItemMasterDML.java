package com.hoho.cheklist.db.dml.p2;

import android.database.sqlite.SQLiteDatabase;

public final class P2ItemMasterDML {

    private P2ItemMasterDML() {}

    public static void insertP2Item(SQLiteDatabase db) {
        db.execSQL(
                "INSERT INTO p2_item_master (" +
                        "item_no, label, use_yn, sort_order" +
                        ") VALUES (" +
                        "1, " +
                        "'제어시스템을 내부방 • 인터넷 등 외부망과 물리적으로 분리 • 운용하고 외부망과 연결접점이 없는가?', " +
                        "1, " +
                        "1" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p2_item_master (" +
                        "item_no, label, use_yn, sort_order" +
                        ") VALUES (" +
                        "2, " +
                        "'각종 시스템에 사용자별 또는 그룹별로 접근권한을 부여하였는가?', " +
                        "1, " +
                        "2" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p2_item_master (" +
                        "item_no, label, use_yn, sort_order" +
                        ") VALUES (" +
                        "3, " +
                        "'패스워드 사용시 안전하게 설정하고 정기적으로 변경 • 사용하는가?', " +
                        "1, " +
                        "3" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p2_item_master (" +
                        "item_no, label, use_yn, sort_order" +
                        ") VALUES (" +
                        "4, " +
                        "'PC • 서버에 불필요한 서비스 포트 제거 및 폴더 공유 금지를 하고 있는가?', " +
                        "1, " +
                        "4" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p2_item_master (" +
                        "item_no, label, use_yn, sort_order" +
                        ") VALUES (" +
                        "5, " +
                        "'PC • 서버에 제어시스템 운용 관련 프로그램만 설치하고 불필요한 프로그램은 제거하였는가?', " +
                        "1, " +
                        "5" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p2_item_master (" +
                        "item_no, label, use_yn, sort_order" +
                        ") VALUES (" +
                        "6, " +
                        "'PC • 서버에 USB 자동실행 기능을 차단하고 있는가?', " +
                        "1, " +
                        "6" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p2_item_master (" +
                        "item_no, label, use_yn, sort_order" +
                        ") VALUES (" +
                        "7, " +
                        "'PC • 서버에 최신 백신 프로그램을 설치하고 전수검사를 하였는가?', " +
                        "1, " +
                        "7" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p2_item_master (" +
                        "item_no, label, use_yn, sort_order" +
                        ") VALUES (" +
                        "8, " +
                        "'서버 • 정보보호시스템 • 네트워크 장비 등에 사고발생시 추적을 위한 로그를 1년 이상 보관하고 있는가?', " +
                        "1, " +
                        "8" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p2_item_master (" +
                        "item_no, label, use_yn, sort_order" +
                        ") VALUES (" +
                        "9, " +
                        "'휴대용 저장매체 관리대장을 현행화하고 관리실태를 점검하였는가?', " +
                        "1, " +
                        "9" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO p2_item_master (" +
                        "item_no, label, use_yn, sort_order" +
                        ") VALUES (" +
                        "10, " +
                        "'제어시스템에 USB 사용을 금지하고 있는가?', " +
                        "1, " +
                        "10" +
                        ");"
        );
    }
}
