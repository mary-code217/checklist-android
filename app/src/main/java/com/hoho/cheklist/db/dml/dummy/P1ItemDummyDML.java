package com.hoho.cheklist.db.dml.dummy;

import android.database.sqlite.SQLiteDatabase;

public class P1ItemDummyDML {
    public static void insertDummyP1Item(SQLiteDatabase db) {
        for (int checklistId = 1; checklistId <= 20; checklistId++) {

            // 결과 패턴: 1=양호, 2=미흡, 3=기타, 4=양호, ...
            String result;
            String remark;

            int mod = (checklistId - 1) % 3;
            if (mod == 0) {
                result = "양호";
                remark = "";
            } else if (mod == 1) {
                result = "미흡";
                remark = "";
            } else {
                result = "기타";
                remark = "기타 비고 내용 " + checklistId;
            }

            String sql =
                    "INSERT INTO checklist_p1_item (" +
                            "checklist_id, " +
                            "section_no, item_no, " +
                            "sec_category, sec_main_question, sec_target, sec_description, sec_reference, " +
                            "evidence, detail_desc, good_case, weak_case, " +
                            "result, remark" +
                            ") VALUES (" +
                            checklistId + ", " +
                            "1, 1, " + // 섹션 1, 항목 1-1만 더미로 생성
                            "'p1 섹션 1 구분(" + checklistId + ")', " +
                            "'p1 섹션 1-1 메인 질문 스냅샷(" + checklistId + ")', " +
                            "'p1 섹션 1-1 점검대상 스냅샷(" + checklistId + ")', " +
                            "'p1 섹션 1-1 설명 스냅샷(" + checklistId + ")', " +
                            "'p1 섹션 1-1 관련근거 스냅샷(" + checklistId + ")', " +

                            "'p1 1-1 증빙 예시(" + checklistId + ")', " +
                            "'p1 1-1 상세 점검 내용 예시(" + checklistId + ")', " +
                            "'p1 1-1 양호 기준 예시(" + checklistId + ")', " +
                            "'p1 1-1 취약 사례 예시(" + checklistId + ")', " +

                            "'" + result + "', " +
                            "'" + remark + "'" +
                            ");";

            db.execSQL(sql);
        }
    }
}
