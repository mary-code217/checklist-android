package com.hoho.cheklist.service.save;

import com.hoho.cheklist.db.AppDBHelper;
import com.hoho.cheklist.db.repository.detail.ChecklistHeaderRepository;
import com.hoho.cheklist.dto.checklist.ChecklistEntity;
import com.hoho.cheklist.dto.checklist.ChecklistHeaderSaveRequest;
import com.hoho.cheklist.util.DateTimeUtil;

public class HeaderSaveService {

    private final ChecklistHeaderRepository headerRepository;

    public HeaderSaveService(AppDBHelper dbHelper) {
        this.headerRepository = new ChecklistHeaderRepository(dbHelper);
    }

    public long createChecklistHeader(ChecklistHeaderSaveRequest request) {
        String now = DateTimeUtil.nowDateTime(); // "yyyy-MM-dd HH:mm:ss" 등

        ChecklistEntity entity = ChecklistEntity.create(
                0L,                // id는 의미 없으니 0
                request.getTitle(),
                request.getInspectedAt(),
                request.getDepartment(),
                request.getSystemName(),
                request.getInspector(),
                0,                 // stage = 0 (진행중)
                now,
                now
        );

        return headerRepository.insert(entity);
    }
}
