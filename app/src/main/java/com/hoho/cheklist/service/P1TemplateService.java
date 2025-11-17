package com.hoho.cheklist.service;

import android.database.sqlite.SQLiteDatabase;

import com.hoho.cheklist.db.AppDBHelper;
import com.hoho.cheklist.db.repository.P1TemplateRepository;
import com.hoho.cheklist.dto.P1.P1ItemRequest;
import com.hoho.cheklist.dto.P1.P1SectionRequest;
import com.hoho.cheklist.dto.P1.P1SectionWithItems;

import java.util.List;

public class P1TemplateService {

    private final AppDBHelper helper;
    private final P1TemplateRepository p1Repository;

    public P1TemplateService(AppDBHelper helper, P1TemplateRepository p1Repository) {
        this.helper = helper;
        this.p1Repository = p1Repository;
    }

    public P1SectionWithItems loadP1Section(int sectionNo) {
        P1SectionRequest section = p1Repository.findSectionNo(sectionNo);
        if (section == null) {
            // 섹션이 없으면 null 리턴 or 예외 던지기
            return null;
        }

        List<P1ItemRequest> items = p1Repository.findSectionItems(section.getId());

        return P1SectionWithItems.create(section, items);
    }

    public void UpdateP1Section(P1SectionWithItems request) {
        SQLiteDatabase db = helper.getWritableDatabase();

        db.beginTransaction();
        try {
            p1Repository.updateSection(request.getSection());
            p1Repository.updateItems(request.getItems());

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
}
