package com.hoho.cheklist.db.dml.dummy;

import android.database.sqlite.SQLiteDatabase;

public class ChecklistDummyDML {
    public static void insertDummyChecklists(SQLiteDatabase db) {
        db.execSQL(
                "INSERT INTO checklist (" +
                        "title, inspected_at, department, system_name, inspector, stage, created_at, updated_at" +
                        ") VALUES (" +
                        "'2021년 1분기 보안점검 체크리스트', " +
                        "'2021-03-25 10:00:00', " +
                        "'정보보안팀', " +
                        "'통합보안시스템', " +
                        "'홍길동', " +
                        "2, " +
                        "'2021-03-25 10:00:00', " +
                        "'2021-03-25 10:00:00'" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO checklist (" +
                        "title, inspected_at, department, system_name, inspector, stage, created_at, updated_at" +
                        ") VALUES (" +
                        "'2021년 2분기 보안점검 체크리스트', " +
                        "'2021-06-24 10:00:00', " +
                        "'정보보안팀', " +
                        "'업무포털', " +
                        "'김보안', " +
                        "2, " +
                        "'2021-06-24 10:00:00', " +
                        "'2021-06-24 10:00:00'" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO checklist (" +
                        "title, inspected_at, department, system_name, inspector, stage, created_at, updated_at" +
                        ") VALUES (" +
                        "'2021년 3분기 보안점검 체크리스트', " +
                        "'2021-09-27 10:00:00', " +
                        "'전산운영팀', " +
                        "'서버실', " +
                        "'이점검', " +
                        "2, " +
                        "'2021-09-27 10:00:00', " +
                        "'2021-09-27 10:00:00'" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO checklist (" +
                        "title, inspected_at, department, system_name, inspector, stage, created_at, updated_at" +
                        ") VALUES (" +
                        "'2021년 4분기 보안점검 체크리스트', " +
                        "'2021-12-20 10:00:00', " +
                        "'전산운영팀', " +
                        "'네트워크장비', " +
                        "'박안전', " +
                        "2, " +
                        "'2021-12-20 10:00:00', " +
                        "'2021-12-20 10:00:00'" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO checklist (" +
                        "title, inspected_at, department, system_name, inspector, stage, created_at, updated_at" +
                        ") VALUES (" +
                        "'2022년 1분기 보안점검 체크리스트', " +
                        "'2022-03-23 10:00:00', " +
                        "'정보보안팀', " +
                        "'통합보안시스템', " +
                        "'홍길동', " +
                        "2, " +
                        "'2022-03-23 10:00:00', " +
                        "'2022-03-23 10:00:00'" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO checklist (" +
                        "title, inspected_at, department, system_name, inspector, stage, created_at, updated_at" +
                        ") VALUES (" +
                        "'2022년 2분기 보안점검 체크리스트', " +
                        "'2022-06-22 10:00:00', " +
                        "'정보보안팀', " +
                        "'업무포털', " +
                        "'김보안', " +
                        "2, " +
                        "'2022-06-22 10:00:00', " +
                        "'2022-06-22 10:00:00'" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO checklist (" +
                        "title, inspected_at, department, system_name, inspector, stage, created_at, updated_at" +
                        ") VALUES (" +
                        "'2022년 3분기 보안점검 체크리스트', " +
                        "'2022-09-26 10:00:00', " +
                        "'전산운영팀', " +
                        "'서버실', " +
                        "'이점검', " +
                        "2, " +
                        "'2022-09-26 10:00:00', " +
                        "'2022-09-26 10:00:00'" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO checklist (" +
                        "title, inspected_at, department, system_name, inspector, stage, created_at, updated_at" +
                        ") VALUES (" +
                        "'2022년 4분기 보안점검 체크리스트', " +
                        "'2022-12-19 10:00:00', " +
                        "'전산운영팀', " +
                        "'네트워크장비', " +
                        "'박안전', " +
                        "2, " +
                        "'2022-12-19 10:00:00', " +
                        "'2022-12-19 10:00:00'" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO checklist (" +
                        "title, inspected_at, department, system_name, inspector, stage, created_at, updated_at" +
                        ") VALUES (" +
                        "'2023년 1분기 보안점검 체크리스트', " +
                        "'2023-03-24 10:00:00', " +
                        "'정보보안팀', " +
                        "'통합보안시스템', " +
                        "'홍길동', " +
                        "2, " +
                        "'2023-03-24 10:00:00', " +
                        "'2023-03-24 10:00:00'" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO checklist (" +
                        "title, inspected_at, department, system_name, inspector, stage, created_at, updated_at" +
                        ") VALUES (" +
                        "'2023년 2분기 보안점검 체크리스트', " +
                        "'2023-06-23 10:00:00', " +
                        "'정보보안팀', " +
                        "'업무포털', " +
                        "'김보안', " +
                        "2, " +
                        "'2023-06-23 10:00:00', " +
                        "'2023-06-23 10:00:00'" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO checklist (" +
                        "title, inspected_at, department, system_name, inspector, stage, created_at, updated_at" +
                        ") VALUES (" +
                        "'2023년 3분기 보안점검 체크리스트', " +
                        "'2023-09-25 10:00:00', " +
                        "'전산운영팀', " +
                        "'서버실', " +
                        "'이점검', " +
                        "2, " +
                        "'2023-09-25 10:00:00', " +
                        "'2023-09-25 10:00:00'" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO checklist (" +
                        "title, inspected_at, department, system_name, inspector, stage, created_at, updated_at" +
                        ") VALUES (" +
                        "'2023년 4분기 보안점검 체크리스트', " +
                        "'2023-12-21 10:00:00', " +
                        "'전산운영팀', " +
                        "'네트워크장비', " +
                        "'박안전', " +
                        "2, " +
                        "'2023-12-21 10:00:00', " +
                        "'2023-12-21 10:00:00'" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO checklist (" +
                        "title, inspected_at, department, system_name, inspector, stage, created_at, updated_at" +
                        ") VALUES (" +
                        "'2024년 1분기 보안점검 체크리스트', " +
                        "'2024-03-22 10:00:00', " +
                        "'정보보안팀', " +
                        "'통합보안시스템', " +
                        "'홍길동', " +
                        "2, " +
                        "'2024-03-22 10:00:00', " +
                        "'2024-03-22 10:00:00'" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO checklist (" +
                        "title, inspected_at, department, system_name, inspector, stage, created_at, updated_at" +
                        ") VALUES (" +
                        "'2024년 2분기 보안점검 체크리스트', " +
                        "'2024-06-21 10:00:00', " +
                        "'정보보안팀', " +
                        "'업무포털', " +
                        "'김보안', " +
                        "2, " +
                        "'2024-06-21 10:00:00', " +
                        "'2024-06-21 10:00:00'" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO checklist (" +
                        "title, inspected_at, department, system_name, inspector, stage, created_at, updated_at" +
                        ") VALUES (" +
                        "'2024년 3분기 보안점검 체크리스트', " +
                        "'2024-09-23 10:00:00', " +
                        "'전산운영팀', " +
                        "'서버실', " +
                        "'이점검', " +
                        "1, " +
                        "'2024-09-23 10:00:00', " +
                        "'2024-09-24 14:30:00'" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO checklist (" +
                        "title, inspected_at, department, system_name, inspector, stage, created_at, updated_at" +
                        ") VALUES (" +
                        "'2024년 4분기 보안점검 체크리스트', " +
                        "'2024-12-19 10:00:00', " +
                        "'전산운영팀', " +
                        "'네트워크장비', " +
                        "'박안전', " +
                        "1, " +
                        "'2024-12-19 10:00:00', " +
                        "'2024-12-20 09:10:00'" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO checklist (" +
                        "title, inspected_at, department, system_name, inspector, stage, created_at, updated_at" +
                        ") VALUES (" +
                        "'2025년 1분기 보안점검 체크리스트', " +
                        "'2025-03-24 10:00:00', " +
                        "'정보보안팀', " +
                        "'통합보안시스템', " +
                        "'홍길동', " +
                        "2, " +
                        "'2025-03-24 10:00:00', " +
                        "'2025-03-24 11:20:00'" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO checklist (" +
                        "title, inspected_at, department, system_name, inspector, stage, created_at, updated_at" +
                        ") VALUES (" +
                        "'2025년 2분기 보안점검 체크리스트', " +
                        "'2025-06-23 10:00:00', " +
                        "'정보보안팀', " +
                        "'업무포털', " +
                        "'김보안', " +
                        "2, " +
                        "'2025-06-23 10:00:00', " +
                        "'2025-06-23 15:05:00'" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO checklist (" +
                        "title, inspected_at, department, system_name, inspector, stage, created_at, updated_at" +
                        ") VALUES (" +
                        "'2025년 3분기 보안점검 체크리스트', " +
                        "'2025-09-25 10:00:00', " +
                        "'전산운영팀', " +
                        "'서버실', " +
                        "'이점검', " +
                        "1, " +
                        "'2025-09-25 10:00:00', " +
                        "'2025-09-25 16:40:00'" +
                        ");"
        );

        db.execSQL(
                "INSERT INTO checklist (" +
                        "title, inspected_at, department, system_name, inspector, stage, created_at, updated_at" +
                        ") VALUES (" +
                        "'2025년 4분기 보안점검 체크리스트', " +
                        "'2025-10-30 10:00:00', " +
                        "'정보보안팀', " +
                        "'통합보안시스템', " +
                        "'홍길동', " +
                        "0, " +
                        "'2025-10-30 10:00:00', " +
                        "'2025-10-30 10:00:00'" +
                        ");"
        );
    }

}
