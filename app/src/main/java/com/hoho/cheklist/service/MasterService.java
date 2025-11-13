package com.hoho.cheklist.service;

import com.hoho.cheklist.db.repository.MasterRepository;
import com.hoho.cheklist.dto.P1ItemRequest;
import com.hoho.cheklist.dto.P1SectionRequest;
import com.hoho.cheklist.dto.P1SectionWithItems;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MasterService {

    private final MasterRepository masterRepository;

    public MasterService(MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }

    // STEP1 대항목 전체 조회
    public JSONArray getP1Section() {
        List<P1SectionRequest> list = masterRepository.findP1Section();
        JSONArray arr = new JSONArray();

        list.forEach(o -> {
            try {
                arr.put(o.toJson());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        return arr;
    }

    // STEP1 대항목 + 하위항목 전체 조회
    public List<P1SectionWithItems> loadP1Structure() {
        // 1) 활성 대항목 전체 조회
        List<P1SectionRequest> sections = masterRepository.findP1Section();

        // 2) 활성 하위항목 전체 조회
        List<P1ItemRequest> items = masterRepository.findP1Item();

        // 3) 섹션 ID별로 하위항목을 묶기 위한 Map 생성
        //    LinkedHashMap을 쓰면 섹션 순서(ORDER BY sort_order)가 그대로 유지됨
        Map<Long, List<P1ItemRequest>> itemsBySectionId = new LinkedHashMap<>();

        for (P1SectionRequest section : sections) {
            itemsBySectionId.put(section.getId(), new ArrayList<>());
        }

        // 4) 하위항목들을 sectionId 기준으로 분배
        for (P1ItemRequest item : items) {
            List<P1ItemRequest> list = itemsBySectionId.get(item.getSectionId());
            if (list != null) {
                list.add(item);
            }
            // 만약 list == null 이면:
            // - 해당 section이 use_yn=0이라 sections에 안 실렸거나
            // - FK가 깨진 데이터 → 지금은 그냥 무시
        }

        // 5) 섹션 순서대로 P1SectionWithItems 리스트 생성
        List<P1SectionWithItems> result = new ArrayList<>();

        for (P1SectionRequest section : sections) {
            List<P1ItemRequest> sectionItems = itemsBySectionId.get(section.getId());
            if (sectionItems == null) {
                sectionItems = new ArrayList<>();
            }
            result.add(P1SectionWithItems.create(section, sectionItems));
        }

        return result;
    }
}
