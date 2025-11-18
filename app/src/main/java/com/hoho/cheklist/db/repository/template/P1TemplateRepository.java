package com.hoho.cheklist.db.repository.template;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hoho.cheklist.db.AppDBHelper;
import com.hoho.cheklist.dto.P1.P1ItemRequest;
import com.hoho.cheklist.dto.P1.P1SectionRequest;

import java.util.ArrayList;
import java.util.List;

public class P1TemplateRepository {

    private final AppDBHelper helper;

    public P1TemplateRepository(AppDBHelper helper) {
        this.helper = helper;
    }

    // 해당 번호의 대항목 조회
    public P1SectionRequest findSectionNo(int sectionNo) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] args = {String.valueOf(sectionNo)};

        try (Cursor c = db.rawQuery(
                "SELECT id, section_no, category, main_question, target, description, reference_basis, use_yn, sort_order " +
                        "FROM p1_section_master " +
                        "WHERE section_no=? AND use_yn = 1 " +
                        "ORDER BY sort_order"
                , args)) {

            if (c.moveToNext()) {
                P1SectionRequest request = P1SectionRequest.create(
                        c.getLong(0),
                        c.getInt(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5),
                        c.getString(6),
                        c.getInt(7),
                        c.getInt(8)
                );

                return request;
            }
        }

        return null;
    }

    // 해당 대항목의 하위항목 가져오기
    public List<P1ItemRequest> findSectionItems(long sectionId) {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<P1ItemRequest> list = new ArrayList<>();

        String[] args = {String.valueOf(sectionId)};

        try(Cursor c = db.rawQuery(
                "SELECT id, section_id, item_no, evidence, detail_desc, good_case, weak_case, use_yn, sort_order " +
                        "FROM p1_item_master " +
                        "WHERE use_yn = 1 AND section_id=? " +
                        "ORDER BY sort_order"
                , args)) {
            while(c.moveToNext()) {
                P1ItemRequest request = P1ItemRequest.create(
                        c.getLong(0),
                        c.getLong(1),
                        c.getInt(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5),
                        c.getString(6),
                        c.getInt(7),
                        c.getInt(8)
                );
                list.add(request);
            }
        }

        return list;
    }

    // 대항목 업데이트
    public void updateSection(P1SectionRequest section) {
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("section_no", section.getSectionNo());
        values.put("category", section.getCategory());
        values.put("main_question", section.getMainQuestion());
        values.put("target", section.getTarget());
        values.put("description", section.getDescription());
        values.put("reference_basis", section.getReferenceBasis());
        values.put("use_yn", section.getUseYn());
        values.put("sort_order", section.getSortOrder());

        // WHERE id = ?
        String[] whereArgs = { String.valueOf(section.getId()) };

        db.update(
                "p1_section_master",
                values,
                "id = ?",
                whereArgs
        );
    }

    // 하위항목 업데이트
    public void updateItems(List<P1ItemRequest> items) {
        if (items == null || items.isEmpty()) {
            return;
        }

        SQLiteDatabase db = helper.getWritableDatabase();

        for (P1ItemRequest item : items) {
            ContentValues values = new ContentValues();
            values.put("section_id", item.getSectionId());    // 필드/게터 형태에 맞게
            values.put("item_no", item.getItemNo());
            values.put("evidence", item.getEvidence());
            values.put("detail_desc", item.getDetailDesc());
            values.put("good_case", item.getGoodCase());
            values.put("weak_case", item.getWeakCase());
            values.put("use_yn", item.getUseYn());
            values.put("sort_order", item.getSortOrder());

            String[] whereArgs = {String.valueOf(item.getId())};

            db.update(
                    "p1_item_master",
                    values,
                    "id = ?",
                    whereArgs
            );
        }
    }
}
