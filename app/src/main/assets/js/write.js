// 전체 페이지 배열 (1-1, 1-2, 1-3, 2-1, ...)
let p1Pages = [];
let currentPageIndex = 0;

/**
 * 안드로이드 브릿지에서 호출하는 함수
 */
window.setP1Structure = function (data) {
    // data 안전 체크
    if (!Array.isArray(data) || data.length === 0) {
        console.error('setP1Structure: empty data', data);

        if (typeof stopLoading === 'function') {
            stopLoading();
        }
        return;
    }

    // 1) 섹션 + 하위항목들을 "페이지" 단위로 평탄화
    p1Pages = [];

    data.forEach(secEntry => {
        const section = secEntry.section;
        const items = secEntry.items || [];

        items.forEach(item => {
            const label = section.sectionNo + '-' + item.itemNo; // 예: "1-1"
            p1Pages.push({
                section,
                item,
                label
            });
        });
    });

    if (p1Pages.length === 0) {
        console.error('setP1Structure: no items in sections', data);

        if (typeof stopLoading === 'function') {
            stopLoading();
        }
        return;
    }

    // 2) 드롭다운 옵션 채우기
    buildPageDropdown();

    // 3) 첫 페이지 렌더링
    currentPageIndex = 0;
    renderCurrentPage();

    if (typeof stopLoading === 'function') {
        stopLoading();
    }
};

/**
 * 드롭다운(#page-indicator) 옵션 구성
 */
function buildPageDropdown() {
    const select = document.getElementById('page-indicator');
    if (!select) return;

    select.innerHTML = ''; // 초기화

    p1Pages.forEach((page, index) => {
        const opt = document.createElement('option');
        opt.value = String(index);        // index로 관리
        opt.textContent = page.label;     // "1-1", "1-2" ...
        select.appendChild(opt);
    });

    select.value = String(currentPageIndex);
}

/**
 * 현재 페이지(p1Pages[currentPageIndex])를 화면에 반영
 */
function renderCurrentPage() {
    const page = p1Pages[currentPageIndex];
    if (!page) return;

    const section = page.section;
    const item = page.item;

    // 상단 대항목 영역
    setText('section_no', section.sectionNo);
    setText('category', section.category);
    setText('main_question', section.mainQuestion);
    setText('target', section.target);
    setText('description', section.description);
    setText('reference_basis', section.referenceBasis);

    // 상세 점검 항목 영역
    setText('itemNo', page.label);              // "1-1" 같은 라벨
    setText('evidence', item.evidence);
    setText('detailDesc', item.detailDesc);
    setText('goodCase', item.goodCase);
    setText('weakCase', item.weakCase);

    // 라디오 버튼 초기화 + name 재설정 (각 페이지별로 구분)
    const radios = document.querySelectorAll('input[type="radio"]');
    radios.forEach(r => {
        r.checked = false;
        r.name = 'check_' + page.label;  // 예: check_1-1, check_1-2 ...
    });

    // 비고 입력 초기화 (나중에 저장 로직 붙일 예정)
    const etcInput = document.querySelector('.etc input[type="text"]');
    if (etcInput) {
        etcInput.value = '';
        etcInput.disabled = true;
    }

    // 드롭다운 선택값 동기화
    const select = document.getElementById('page-indicator');
    if (select && select.value !== String(currentPageIndex)) {
        select.value = String(currentPageIndex);
    }

    updateNavButtons();
}

/**
 * 이전 / 다음 버튼 상태 업데이트(선택사항: 비활성화 스타일 등)
 */
function updateNavButtons() {
    const prevBtn = document.querySelector('.btm_box a:nth-child(1)');
    const nextBtn = document.querySelector('.btm_box a:nth-child(2)');

    if (prevBtn) {
        if (currentPageIndex === 0) {
            prevBtn.classList.add('disabled');
        } else {
            prevBtn.classList.remove('disabled');
        }
    }

    if (nextBtn) {
        if (currentPageIndex >= p1Pages.length - 1) {
            nextBtn.classList.add('disabled');
        } else {
            nextBtn.classList.remove('disabled');
        }
    }
}

/**
 * 공통 텍스트 세터
 */
function setText(id, value) {
    const el = document.getElementById(id);
    if (!el) return;
    el.textContent = value == null ? '' : String(value);
}

/**
 * 하단 "이전" 버튼 클릭 핸들러
 *  - HTML에서: <a onclick="prevSection()">이전</a>
 */
function prevSection() {
    if (currentPageIndex > 0) {
        currentPageIndex--;
        renderCurrentPage();

        window.scrollTo({
          top: 0
        });
    }
}

/**
 * 하단 "다음" 버튼 클릭 핸들러
 *  - HTML에서: <a onclick="nextSection()">다음</a>
 */
function nextSection() {
    if (currentPageIndex < p1Pages.length - 1) {
        currentPageIndex++;
        renderCurrentPage();

        window.scrollTo({
          top: 0
        });
    }
}

// 현재 페이지의 P1 항목 id를 얻는 함수
// Android 쪽에서 window.currentP1ItemId 를 채워주는 방식으로 맞추면 제일 단순함.
function getCurrentP1ItemId() {
    const page = p1Pages[currentPageIndex];
    if (page && page.item && typeof page.item.id === 'number') {
        return page.item.id;   // ← 안드로이드가 내려주는 item.id 사용
    }
    return null;
}

// 이미지 박스 클릭 이벤트 설정
function setupP1PhotoBoxes() {
    const boxes = document.querySelectorAll('.img_box .box');
    if (!boxes || boxes.length === 0) return;

    boxes.forEach(box => {
        const slotAttr = box.getAttribute('data-slot');
        const slotIndex = parseInt(slotAttr, 10);
        if (Number.isNaN(slotIndex)) {
            return;
        }

        box.addEventListener('click', () => {
            const p1ItemId = getCurrentP1ItemId();
            if (p1ItemId == null) {
                alert('현재 항목 ID를 찾을 수 없습니다.');
                return;
            }

            if (window.photo && typeof window.photo.pickP1Photo === 'function') {
                // 안드로이드 PhotoBridge 호출
                window.photo.pickP1Photo(p1ItemId, slotIndex);
            } else {
                alert('사진 기능이 아직 준비되지 않았습니다.');
            }
        });
    });
}

// 브릿지에서 사진 저장 후 호출하는 콜백
//  - slotIndex : 0 ~ 3
//  - imagePath : Android에서 넘겨준 경로(String)
//  - photoId   : DB에 저장된 photo row id
window.setP1PhotoSlot = function (slotIndex, imagePath, photoId) {
    const selector = '.img_box .box[data-slot="' + slotIndex + '"]';
    const box = document.querySelector(selector);
    if (!box) return;

    const img = box.querySelector('img');
    if (!img) return;

    // 1차 버전: 경로를 그대로 src에 넣어본다.
    // (content:// 경로는 바로 안 보일 수 있고, 나중에 base64 등으로 바꿀지 결정)
    img.src = imagePath;

    // 나중에 삭제 기능 등을 위해 photoId도 기억해두기
    img.dataset.photoId = String(photoId);
};

/**
 * DOM 로드 후 브릿지 호출 + 드롭다운 change 이벤트 설정
 */
document.addEventListener('DOMContentLoaded', () => {
    startLoading()

    // 안드로이드 브릿지 호출
    if (window.Setting && typeof window.Setting.loadP1Structure === 'function') {
        window.Setting.loadP1Structure();
    } else {
        console.error('Android bridge "Setting" or "loadP1Structure" not found');
    }

    // 드롭다운에서 페이지 선택 시 이동
    const select = document.getElementById('page-indicator');
    if (select) {
        select.addEventListener('change', (e) => {
            const idx = parseInt(e.target.value, 10);
            if (!isNaN(idx) && idx >= 0 && idx < p1Pages.length) {
                currentPageIndex = idx;
                renderCurrentPage();
            }
        });
    }

    // 라디오 상태에 따라 비고 활성/비활성 처리
    const radios = document.querySelectorAll('.layout_04 input[type="radio"]');
    const etcInput = document.getElementById('etcText');

    if (etcInput && radios.length) {
        radios.forEach(radio => {
            radio.addEventListener('change', () => {
                if (radio.value === '기타' && radio.checked) {
                    // 기타 선택 시 비고 활성
                    etcInput.disabled = false;
                } else if (radio.checked) {
                    // 양호/미흡 선택 시 비고 비활성 + 값 초기화
                    etcInput.disabled = true;
                    etcInput.value = '';
                }
            });
        });
    }

    setupP1PhotoBoxes();
});