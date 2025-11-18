package com.hoho.cheklist.service.main;

import com.hoho.cheklist.db.repository.main.ChecklistRepository;
import com.hoho.cheklist.dto.checklist.ChecklistEntity;
import com.hoho.cheklist.dto.checklist.ChecklistPageView;
import com.hoho.cheklist.dto.checklist.ChecklistView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChecklistQueryService {

    private static final int PAGE_SIZE = 10;
    private final ChecklistRepository checklistRepository;

    public ChecklistQueryService(ChecklistRepository checklistRepository) {
        this.checklistRepository = checklistRepository;
    }

    public ChecklistPageView getChecklistPage(int page) {
        // 1) 페이지 보정
        if (page < 1) page = 1;

        // 2) 전체 개수 조회
        long totalElements = checklistRepository.countAll();

        // 3) 전체 페이지 계산
        int totalPages;
        if (totalElements == 0L) {
            totalPages = 0;
        } else {
            totalPages = (int) ((totalElements + PAGE_SIZE - 1) / PAGE_SIZE);
        }

        // 4) 데이터가 없으면 빈 결과 리턴
        if (totalPages == 0) {
            return ChecklistPageView.create(
                    Collections.emptyList(),
                    1,
                    PAGE_SIZE,
                    0,
                    0L,
                    false,
                    false
            );
        }

        // 5) 요청 페이지가 범위를 넘으면 마지막 페이지로 보정
        if (page > totalPages) {
            page = totalPages;
        }

        // 6) 해당 페이지 데이터 조회
        List<ChecklistEntity> entities = checklistRepository.findPage(page, PAGE_SIZE);

        // 7) Entity → View 변환
        List<ChecklistView> views = new ArrayList<>();

        // ★ 여기서부터 번호 계산
        int startIndex = (page - 1) * PAGE_SIZE; // 이 페이지의 첫 번째 globalIndex

        for (int i = 0; i < entities.size(); i++) {
            ChecklistEntity entity = entities.get(i);

            long displayNoValue = totalElements - (startIndex + i);
            if (displayNoValue < 1) {
                displayNoValue = 1; // 안전빵 가드
            }

            views.add(toView(entity, String.valueOf(displayNoValue)));
        }

        // 8) 이전/다음 페이지 여부 계산
        boolean hasPrev = page > 1;
        boolean hasNext = page < totalPages;


        // 9) 최종 DTO로 묶어서 반환
        return ChecklistPageView.create(
                views,
                page,
                PAGE_SIZE,
                totalPages,
                totalElements,
                hasPrev,
                hasNext
        );
    }

    private ChecklistView toView(ChecklistEntity entity, String no) {
        long id = entity.getId();

        String title = entity.getTitle();

        String statusLabel = mapStageToStatusLabel(entity.getStage());

        String createdDateLabel = formatDateLabel(entity.getCreatedAt());

        return ChecklistView.create(
                id,
                no,
                title,
                statusLabel,
                createdDateLabel,
                entity.getStage()
        );
    }

    private String mapStageToStatusLabel(int stage) {
        switch (stage) {
            case 0:
                return "작성중";
            case 1:
                return "1단계 완료";
            case 2:
                return "2단계 완료";
            default:
                return "최종 완료";
        }
    }

    private String formatDateLabel(String createdAt) {
        if (createdAt == null) {
            return "";
        }
        if (createdAt.length() < 10) {
            return createdAt; // 안전하게 원본 그대로
        }

        String datePart = createdAt.substring(0, 10); // "YYYY-MM-DD"
        String[] parts = datePart.split("-");
        if (parts.length != 3) {
            return datePart;
        }

        String year = parts[0];
        String month = parts[1];
        String day = parts[2];

        return year + ". " + month + ". " + day;
    }
}
