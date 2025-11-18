package com.hoho.cheklist.service.main;

import com.hoho.cheklist.db.repository.main.ChecklistRepository;

import java.util.List;

public class ChecklistModifyService {

    private final ChecklistRepository checklistRepository;

    public ChecklistModifyService(ChecklistRepository checklistRepository) {
        this.checklistRepository = checklistRepository;
    }

    /**
     * 체크리스트 삭제 (단일/다수 공통)
     * - ids 크기가 1이면 단일 삭제, 2 이상이면 다수 삭제
     */
    public int deleteChecklists(List<Long> ids) {
        // 1) null/빈 리스트 방어
        if (ids == null || ids.isEmpty()) {
            return 0;
        }

        // 2) TODO: 나중에 상세/사진 테이블까지 함께 삭제해야 하면
        //    여기서 다른 Repository도 함께 호출할 예정
        //    ex)
        //    detailRepository.deleteByChecklistIds(ids);
        //    photoRepository.deleteByChecklistIds(ids);

        // 3) checklist 헤더 삭제
        return checklistRepository.deleteByIds(ids);
    }
}
