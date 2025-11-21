package com.hoho.cheklist.service.detail;

import com.hoho.cheklist.db.AppDBHelper;
import com.hoho.cheklist.db.repository.detail.ChecklistHeaderRepository;
import com.hoho.cheklist.db.repository.detail.P1ItemRepository;
import com.hoho.cheklist.db.repository.detail.P1PhotoRepository;
import com.hoho.cheklist.db.repository.detail.P2ItemRepository;
import com.hoho.cheklist.db.repository.detail.P2PhotoRepository;
import com.hoho.cheklist.dto.detail.ChecklistDetailDto;
import com.hoho.cheklist.dto.detail.P1ItemDto;
import com.hoho.cheklist.dto.detail.P1PhotoDto;
import com.hoho.cheklist.dto.detail.P2ItemDto;
import com.hoho.cheklist.dto.detail.P2PhotoDto;

import java.util.List;
import java.util.Map;

public class DetailService {
    private final ChecklistHeaderRepository headerRepository;
    private final P1ItemRepository p1ItemRepository;
    private final P1PhotoRepository p1PhotoRepository;
    private final P2ItemRepository p2ItemRepository;
    private final P2PhotoRepository p2PhotoRepository;

    public DetailService(AppDBHelper helper) {
        this.headerRepository = new ChecklistHeaderRepository(helper);
        this.p1ItemRepository = new P1ItemRepository(helper);
        this.p1PhotoRepository = new P1PhotoRepository(helper);
        this.p2ItemRepository = new P2ItemRepository(helper);
        this.p2PhotoRepository = new P2PhotoRepository(helper);
    }

    /**
     * 체크리스트 상세 조회 (헤더 + p1 항목/사진 + P2 항목/사진)
     */
    public ChecklistDetailDto getChecklistDetail(long checklistId) {
        // 1) 헤더
        ChecklistDetailDto header = headerRepository.findHeader(checklistId);
        if (header == null) {
            return null;
        }

        // 2) p1 항목들
        List<P1ItemDto> p1Items = p1ItemRepository.findByChecklistId(checklistId);

        // 3) p1 사진들 (itemId → 사진 리스트)
        Map<Long, List<P1PhotoDto>> p1PhotoMap =
                p1PhotoRepository.findGroupedByChecklistId(checklistId);

        // 4) 각 P1Item에 사진 붙이기
        for (P1ItemDto item : p1Items) {
            List<P1PhotoDto> photos = p1PhotoMap.get(item.getId());
            item.addPhotos(photos);
        }

        // 5) P2 항목/사진
        List<P2ItemDto> p2Items = p2ItemRepository.findByChecklistId(checklistId);
        List<P2PhotoDto> p2Photos = p2PhotoRepository.findByChecklistId(checklistId);

        // 6) DTO에 세팅
        header.addP1Items(p1Items);
        header.addP2Items(p2Items);
        header.addP2Photos(p2Photos);

        return header;
    }


}
