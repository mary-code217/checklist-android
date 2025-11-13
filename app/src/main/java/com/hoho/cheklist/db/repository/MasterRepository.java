package com.hoho.cheklist.db.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hoho.cheklist.db.AppDBHelper;
import com.hoho.cheklist.dto.P1ItemRequest;
import com.hoho.cheklist.dto.P1SectionRequest;

import java.util.ArrayList;
import java.util.List;

public class MasterRepository {

    private final AppDBHelper helper;

    public MasterRepository(AppDBHelper helper) {
        this.helper = helper;
    }

    // 대항목 전체 조회
    public List<P1SectionRequest> findP1Section() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<P1SectionRequest> list = new ArrayList<>();

        try (Cursor c = db.rawQuery(
                "SELECT id, section_no, category, main_question, target, description, reference_basis, use_yn, sort_order " +
                        "FROM p1_section_master WHERE use_yn = 1 ORDER BY sort_order", null)) {

            while (c.moveToNext()) {
                P1SectionRequest s = P1SectionRequest.create(
                        c.getLong(0),
                        c.getInt(1),
                        nullToEmpty(c.getString(2)),
                        nullToEmpty(c.getString(3)),
                        nullToEmpty(c.getString(4)),
                        nullToEmpty(c.getString(5)),
                        nullToEmpty(c.getString(6)),
                        c.getInt(7),
                        c.getInt(8)
                );
                list.add(s);
            }
        }
        return list;
    }

    // 하위 항목 전체조회
    public List<P1ItemRequest> findP1Item() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<P1ItemRequest> list = new ArrayList<>();

        try (Cursor c = db.rawQuery("SELECT id, section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order " +
                "FROM p1_item_master WHERE use_yn = 1 ORDER BY sort_order", null)) {

            while (c.moveToNext()) {
                P1ItemRequest s = P1ItemRequest.create(
                        c.getLong(0),
                        c.getLong(1),
                        c.getInt(2),
                        nullToEmpty(c.getString(3)),
                        nullToEmpty(c.getString(4)),
                        nullToEmpty(c.getString(5)),
                        nullToEmpty(c.getString(6)),
                        c.getInt(7),
                        c.getInt(8)
                );
                list.add(s);
            }
        }
        return list;
    }

    private static String nullToEmpty(String s) {
        return s == null ? "" : s;
    }
}
