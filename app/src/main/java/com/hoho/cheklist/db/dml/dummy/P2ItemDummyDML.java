package com.hoho.cheklist.db.dml.dummy;

import android.database.sqlite.SQLiteDatabase;

public class P2ItemDummyDML {
    public static void insertDummyP2Item(SQLiteDatabase db) {
        for (int checklistId = 1; checklistId <= 20; checklistId++) {

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
                remark = "2단계 기타 비고 내용 " + checklistId;
            }

            String sql =
                    "INSERT INTO checklist_p2_item (" +
                            "checklist_id, item_no, label, result, remark" +
                            ") VALUES (" +
                            checklistId + ", " +
                            "1, " + // 2단계 1번 항목만 더미로 생성
                            "'2단계 항목1 문구 스냅샷(체크리스트 " + checklistId + ")', " +
                            "'" + result + "', " +
                            "'" + remark + "'" +
                            ");";

            db.execSQL(sql);
        }
    }
}
