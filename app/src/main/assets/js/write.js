// 전체 페이지 배열 (1-1, 1-2, 1-3, 2-1, ...)
let p1Pages = [];
let currentPageIndex = 0;

/**
 * 안드로이드 브릿지에서 호출하는 함수
 *  - data: [
 *      { section: {...}, items: [ {...}, {...} ] },
 *      ...
 *    ]
 */
window.setP1Structure = function (data) {
    // data 안전 체크
    if (!Array.isArray(data) || data.length === 0) {
        console.error('setP1Structure: empty data', data);
        return;
    }

    // 1) 섹션 + 하위항목들을 "페이지" 단위로 평탄화
    //    pages: [{ section, item, label: '1-1' }, ...]
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
        return;
    }

    // 2) 드롭다운 옵션 채우기
    buildPageDropdown();

    // 3) 첫 페이지 렌더링
    currentPageIndex = 0;
    renderCurrentPage();
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

/**
 * DOM 로드 후 브릿지 호출 + 드롭다운 change 이벤트 설정
 */
document.addEventListener('DOMContentLoaded', () => {
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

    // 🔹 라디오 상태에 따라 비고 활성/비활성 처리
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

    // 🔹 이미지 박스 클릭 시 안드로이드에 요청
//    const boxes = document.querySelectorAll('.img_box .box');
//    boxes.forEach(box => {
//        box.addEventListener('click', () => {
//            const slot = parseInt(box.dataset.slot, 10);
//
//            // 안드로이드 쪽 JS 브릿지 호출 (이름은 네이티브에서 맞춰줘야 함)
//            if (window.Photo && typeof window.Photo.requestImage === 'function') {
//                window.Photo.requestImage(slot);
//            } else {
//                alert('사진 기능이 아직 준비되지 않았습니다.');
//            }
//        });
//    });
});
