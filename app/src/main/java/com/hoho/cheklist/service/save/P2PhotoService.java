package com.hoho.cheklist.service.save;

import com.hoho.cheklist.db.AppDBHelper;
import com.hoho.cheklist.db.repository.detail.P2PhotoRepository;
import com.hoho.cheklist.dto.detail.P2PhotoDto;

import java.util.List;

/**
 * 2단계(P2) 공통 사진 서비스
 * - 체크리스트 전체에 대해 최대 4장까지 등록
 */
public class P2PhotoService {
    private static final int MAX_PHOTO_PER_CHECKLIST = 4;
    private final P2PhotoRepository p2PhotoRepository;

    public P2PhotoService(AppDBHelper dbHelper) {
        this.p2PhotoRepository = new P2PhotoRepository(dbHelper);
    }

    /**
     * 체크리스트에 사진 1장 추가
     * - 현재 개수를 확인해서 4장 초과 시 예외
     * - 성공 시, 방금 저장된 사진 정보(P2PhotoDto) 반환
     */
    public P2PhotoDto addPhoto(long checklistId, String photoPath) {
        int currentCount = p2PhotoRepository.countByChecklistId(checklistId);
        if (currentCount >= MAX_PHOTO_PER_CHECKLIST) {
            throw new IllegalStateException(
                    "2단계 공통 사진은 체크리스트당 최대 " + MAX_PHOTO_PER_CHECKLIST + "장까지 등록 가능합니다."
            );
        }

        long id = p2PhotoRepository.insert(checklistId, photoPath);
        if (id == -1L) {
            throw new IllegalStateException("2단계 공통 사진 정보를 저장하지 못했습니다.");
        }

        return P2PhotoDto.create(id, checklistId, photoPath);
    }

    /**
     * 특정 체크리스트의 공통 사진 목록 조회
     */
    public List<P2PhotoDto> getPhotos(long checklistId) {
        return p2PhotoRepository.findByChecklistId(checklistId);
    }

    /**
     * 사진 한 장 삭제 (id 기준)
     */
    public void deletePhoto(long photoId) {
        p2PhotoRepository.deleteById(photoId);
    }

    /**
     * 특정 체크리스트의 사진 전체 삭제
     */
    public void deleteAllByChecklist(long checklistId) {
        p2PhotoRepository.deleteAllByChecklistId(checklistId);
    }

}
