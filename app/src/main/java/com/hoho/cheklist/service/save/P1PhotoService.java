package com.hoho.cheklist.service.save;

import com.hoho.cheklist.db.AppDBHelper;
import com.hoho.cheklist.db.repository.detail.P1PhotoRepository;
import com.hoho.cheklist.dto.detail.P1PhotoDto;

import java.util.List;
import java.util.Map;

/**
 * 1단계(P1) 하위 항목별 사진 서비스
 * - 1개 P1 하위 항목당 최대 4장까지 등록
 * - 조회/삭제 등 비즈니스 규칙 담당
 */
public class P1PhotoService {
    // 한 P1 항목당 허용되는 최대 사진 수
    private static final int MAX_PHOTO_PER_ITEM = 4;
    private final P1PhotoRepository p1PhotoRepository;

    public P1PhotoService(AppDBHelper dbHelper) {
        this.p1PhotoRepository = new P1PhotoRepository(dbHelper);
    }

    /**
     * P1 항목에 사진 1장 추가
     * - 현재 개수를 확인해서 4장 초과 시 예외
     * - 성공 시, 방금 저장된 사진 정보(P1PhotoDto) 반환
     */
    public P1PhotoDto addPhoto(long p1ItemId, String photoPath) {
        int currentCount = p1PhotoRepository.countByP1ItemId(p1ItemId);
        if (currentCount >= MAX_PHOTO_PER_ITEM) {
            // 나중에 커스텀 예외로 바꿔도 됨
            throw new IllegalStateException(
                    "사진은 한 항목당 최대 " + MAX_PHOTO_PER_ITEM + "장까지 등록 가능합니다."
            );
        }

        long id = p1PhotoRepository.insert(p1ItemId, photoPath);
        // insert 실패 시 -1 리턴하는 패턴 그대로 쓰는 경우 체크
        if (id == -1L) {
            throw new IllegalStateException("사진 정보를 저장하지 못했습니다.");
        }

        return P1PhotoDto.create(id, p1ItemId, photoPath);
    }

    /**
     * 특정 P1 하위 항목에 연결된 사진 목록 조회
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
     * 특정 P1 항목의 사진 전체 삭제
     */
    public void deleteAllByItem(long p1ItemId) {
        p1PhotoRepository.deleteAllByP1ItemId(p1ItemId);
    }
}
