// URL 쿼리에서 id 꺼내는 함수
function getChecklistIdFromQuery() {
    const search = window.location.search; // "?id=20" 이런 형태
    const params = new URLSearchParams(search);
    const idStr = params.get('id');        // "20" 또는 null

    if (!idStr) {
        return null;
    }

    const id = parseInt(idStr, 10);
    if (Number.isNaN(id)) {
        return null;
    }
    return id;
}

// 상세 데이터 렌더링
function renderChecklistDetail(data) {
    const wrap = document.querySelector('.detail_wrap');
    if (!wrap) return;

    // 이전 내용 초기화
    wrap.innerHTML = '';

    // --- 헤더 영역 ---
    const titleEl = document.createElement('h2');
    titleEl.textContent = data.title || '(제목 없음)';
    wrap.appendChild(titleEl);

    const metaEl = document.createElement('p');
    metaEl.textContent =
        `점검일시: ${data.inspectedAt || '-'} | ` +
        `관리부서: ${data.department || '-'} | ` +
        `점검시스템: ${data.systemName || '-'} | ` +
        `점검자: ${data.inspector || '-'} | ` +
        `진행단계: ${data.stageLabel || data.stage || '-'}`;
    wrap.appendChild(metaEl);

    const createdEl = document.createElement('p');
    createdEl.textContent =
        `생성일: ${data.createdAt || '-'} | 수정일: ${data.updatedAt || '-'}`;
    wrap.appendChild(createdEl);

    // 구분선
    const hr1 = document.createElement('hr');
    wrap.appendChild(hr1);

    // --- 1단계(P1) 항목들 ---
    if (Array.isArray(data.p1Items) && data.p1Items.length > 0) {
        const p1Title = document.createElement('h3');
        p1Title.textContent = '1단계 점검 결과';
        wrap.appendChild(p1Title);

        data.p1Items.forEach((item) => {
            const box = document.createElement('div');
            box.className = 'p1_item';

            const line1 = document.createElement('p');
            line1.textContent =
                `[${item.sectionNo}-${item.itemNo}] ${item.secMainQuestion || ''}`;
            box.appendChild(line1);

            const line2 = document.createElement('p');
            line2.textContent =
                `구분: ${item.secCategory || '-'} | 대상: ${item.secTarget || '-'}`;
            box.appendChild(line2);

            const line3 = document.createElement('p');
            line3.textContent =
                `결과: ${item.result || '-'}`
                + (item.remark ? ` / 비고: ${item.remark}` : '');
            box.appendChild(line3);

            // 사진 경로도 텍스트로 표시
            if (Array.isArray(item.photos) && item.photos.length > 0) {
                const photoLine = document.createElement('p');
                const paths = item.photos.map(p => p.path || p.photoPath || '').join(', ');
                photoLine.textContent = `사진: ${paths}`;
                box.appendChild(photoLine);
            }

            wrap.appendChild(box);
        });

        const hr2 = document.createElement('hr');
        wrap.appendChild(hr2);
    }

    // --- 2단계(P2) 항목들 ---
    if (Array.isArray(data.p2Items) && data.p2Items.length > 0) {
        const p2Title = document.createElement('h3');
        p2Title.textContent = '2단계 점검 결과';
        wrap.appendChild(p2Title);

        data.p2Items.forEach((item) => {
            const box = document.createElement('div');
            box.className = 'p2_item';

            const line1 = document.createElement('p');
            line1.textContent = `[${item.itemNo}] ${item.label || ''}`;
            box.appendChild(line1);

            const line2 = document.createElement('p');
            line2.textContent =
                `결과: ${item.result || '-'}`
                + (item.remark ? ` / 비고: ${item.remark}` : '');
            box.appendChild(line2);

            wrap.appendChild(box);
        });

        const hr3 = document.createElement('hr');
        wrap.appendChild(hr3);
    }

    // --- 2단계 공통 사진 ---
    if (Array.isArray(data.p2Photos) && data.p2Photos.length > 0) {
        const photoTitle = document.createElement('h3');
        photoTitle.textContent = '2단계 공통 사진';
        wrap.appendChild(photoTitle);

        data.p2Photos.forEach((photo) => {
            const p = document.createElement('p');
            p.textContent = `사진 경로: ${photo.photoPath || ''}`;
            wrap.appendChild(p);
        });
    }
}

// 에러 시 호출 (브릿지에서 필요하면 사용)
window.onChecklistDetailError = function (code) {
    const wrap = document.querySelector('.detail_wrap');
    if (!wrap) return;
    wrap.textContent = '데이터를 불러오지 못했습니다. (' + code + ')';
};

// 네이티브에서 호출하는 콜백
window.setChecklistDetail = function (data) {
    renderChecklistDetail(data);
};

// 초기 로딩
document.addEventListener('DOMContentLoaded', function () {
    const id = getChecklistIdFromQuery();
    if (!id) {
        console.error('체크리스트 ID가 없습니다.');
        const wrap = document.querySelector('.detail_wrap');
        if (wrap) {
            wrap.textContent = '잘못된 접근입니다. (ID 없음)';
        }
        return;
    }

    if (window.detail && typeof window.detail.loadChecklistDetail === 'function') {
        window.detail.loadChecklistDetail(id);
    } else {
        console.error('detail.loadChecklistDetail 가 없습니다.');
        const wrap = document.querySelector('.detail_wrap');
        if (wrap) {
            wrap.textContent = '네이티브 브릿지를 찾을 수 없습니다.';
        }
    }
});