package com.hoho.cheklist.service.save;

import android.util.Log;

import com.hoho.cheklist.db.AppDBHelper;
import com.hoho.cheklist.db.repository.detail.P1PhotoRepository;
import com.hoho.cheklist.dto.detail.P1PhotoDto;

import java.util.List;
import java.util.Map;

/**
 * 1단계(p1) 하위 항목별 사진 서비스
 * - 1개 p1 하위 항목당 최대 4장까지 등록
 * - 조회/삭제 등 비즈니스 규칙 담당
 */
public class P1PhotoService {
    // 한 p1 항목당 허용되는 최대 사진 수
    private static final int MAX_PHOTO_PER_ITEM = 4;
    private final P1PhotoRepository p1PhotoRepository;

    public P1PhotoService(AppDBHelper dbHelper) {
        this.p1PhotoRepository = new P1PhotoRepository(dbHelper);
    }

    /**
     * p1 하위 항목별 사진 추가
     * - 한 항목당 최대 4장까지 허용
     * - 5장째부터 IllegalStateException 발생
     */
    public P1PhotoDto addPhoto(long p1ItemId, String photoPath) {
        // 1) 현재 DB에 저장된 사진 개수 조회
        List<P1PhotoDto> existing = p1PhotoRepository.findByP1ItemId(p1ItemId);
        int currentCount = existing.size();

        // 2) 이미 4장 이상이면 추가 불가
        if (currentCount >= MAX_PHOTO_PER_ITEM) {
            throw new IllegalStateException("사진은 한 항목당 최대 4장까지 등록 가능합니다.");
        }

        // 3) 아직 4장 미만이면 INSERT
        long newId = p1PhotoRepository.insert(p1ItemId, photoPath);

        // 4) 방금 저장한 내용 DTO로 리턴
        return P1PhotoDto.create(newId, p1ItemId, photoPath);
    }

    /**
     * 특정 p1 하위 항목에 연결된 사진 목록 조회
     */
    public List<P1PhotoDto> getPhotosByItem(long p1ItemId) {
        return p1PhotoRepository.findByP1ItemId(p1ItemId);
    }

    /**
     * checklist_id 기준으로
     *  p1_item_id → 사진 목록 형태로 전체 조회
     * - 화면 최초 로딩 시 썸네일 복원에 사용
     */
    public Map<Long, List<P1PhotoDto>> getPhotosGroupedByChecklist(long checklistId) {
        return p1PhotoRepository.findGroupedByChecklistId(checklistId);
    }

    /**
     * 사진 한 장 삭제 (id 기준)
     */
    public void deletePhoto(long photoId) {
        p1PhotoRepository.deleteById(photoId);
    }

    /**
     * 특정 p1 항목의 사진 전체 삭제
     */
    public void deleteAllByItem(long p1ItemId) {
        p1PhotoRepository.deleteAllByP1ItemId(p1ItemId);
    }

    /**
     * p1 하위 항목(p1ItemId) 기준으로 사진 전체 조회
     * - 슬롯 초기화/렌더링, 삭제 후 재로딩 등에 사용
     */
    public List<P1PhotoDto> findByP1ItemId(long p1ItemId) {
        return p1PhotoRepository.findByP1ItemId(p1ItemId);
    }
}
