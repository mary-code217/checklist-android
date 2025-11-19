const TOTAL_SECTIONS = 15;
let currentSectionNo = 1;
let currentPageData = null; // { section: {...}, items: [...] }

document.addEventListener('DOMContentLoaded', () => {
    const $sectionSelect = document.getElementById('sectionSelect');
    const $btnPrev = document.querySelector('.btn_prev');
    const $btnNext = document.querySelector('.btn_next');
    const $btnSave = document.getElementById('save');

    // --- 네비게이션은 이전에 만든 걸 그대로 씀 ---
    if ($sectionSelect) {
      $sectionSelect.addEventListener('change', (e) => {
        const no = parseInt(e.target.value, 10);
        if (!isNaN(no)) {
          changeSection(no);
        }
      });
    }

    if ($btnPrev) {
      $btnPrev.addEventListener('click', (e) => {
        e.preventDefault();
        if (currentSectionNo > 1) {
          changeSection(currentSectionNo - 1);
        }
      });
    }

    if ($btnNext) {
      $btnNext.addEventListener('click', (e) => {
        e.preventDefault();
        if (currentSectionNo < TOTAL_SECTIONS) {
          changeSection(currentSectionNo + 1);
        }
      });
    }

    // 저장 버튼
    if ($btnSave) {
      $btnSave.addEventListener('click', onClickSave);
    }

    // 최초 로딩
    changeSection(1);
});

function changeSection(sectionNo) {
    currentSectionNo = sectionNo;

    const $sectionSelect = document.getElementById('sectionSelect');
    if ($sectionSelect) {
      $sectionSelect.value = String(sectionNo);
    }

    if (window.P1Template && typeof window.P1Template.loadTemplate === 'function') {
      if (typeof startLoading === 'function') {
          startLoading();
      }
      window.P1Template.loadTemplate(sectionNo);
    } else {
      console.warn('P1Template bridge not available');
      if (typeof stopLoading === 'function') {
          stopLoading();
      }
    }

    window.scrollTo({ top: 0, left: 0, behavior: 'smooth' });
}

  // 네이티브에서 호출하는 콜백
window.onP1TemplateLoaded = function (page) {
    if (!page) {
      alert('해당 섹션 데이터를 불러오지 못했습니다.');

      if (typeof stopLoading === 'function') {
          stopLoading();
      }
      return;
    }
    currentPageData = page; // 그대로 보관 (id, sortOrder 등 유지용)

    fillSection(page.section);
    renderItems(page.items || []);

    if (typeof stopLoading === 'function') {
        stopLoading();
    }
};

function fillSection(section) {
    if (!section) return;

    document.getElementById('sectionNo').textContent = section.sectionNo;
    document.getElementById('category').value = section.category || '';
    document.getElementById('main_question').value = section.mainQuestion || '';
    document.getElementById('target').value = section.target || '';
    document.getElementById('description').value = section.description || '';
    document.getElementById('reference_basis').value = section.referenceBasis || '';
}

// 하위항목 렌더링 (앞에서 쓴 버전과 동일 컨셉)
function renderItems(items) {
    const tbody = document.getElementById('p1-items-body');
    if (!tbody) return;

    tbody.innerHTML = '';

    items.forEach((item, idx) => {
      const displayNo = currentSectionNo + '-' + item.itemNo;

      const blockHtml = `
        <tr>
          <th rowspan="4" width="100px" class="on_bor">${displayNo}</th>
          <td colspan="2" width="100px">증빙</td>
          <td colspan="2" width="547px" class="align_left">
            <input type="text"
                   class="change_input"
                   data-item-index="${idx}"
                   data-field="evidence"
                   value="${escapeHtml(item.evidence || '')}">
          </td>
          <td rowspan="4" width="70px"><input type="radio" disabled></td>
          <td rowspan="4" width="70px"><input type="radio" disabled></td>
          <td rowspan="4" width="70px" class="not_bor_right"><input type="radio" disabled></td>
        </tr>
        <tr>
          <td colspan="2">설명</td>
          <td colspan="2" class="align_left">
            <input type="text"
                   class="change_input"
                   data-item-index="${idx}"
                   data-field="detailDesc"
                   value="${escapeHtml(item.detailDesc || '')}">
          </td>
        </tr>
        <tr>
          <td rowspan="2">판정</td>
          <td>양호</td>
          <td class="align_left">
            <input type="text"
                   class="change_input"
                   data-item-index="${idx}"
                   data-field="goodCase"
                   value="${escapeHtml(item.goodCase || '')}">
          </td>
        </tr>
        <tr>
          <td>취약</td>
          <td class="align_left">
            <input type="text"
                   class="change_input"
                   data-item-index="${idx}"
                   data-field="weakCase"
                   value="${escapeHtml(item.weakCase || '')}">
          </td>
        </tr>
      `;
      tbody.insertAdjacentHTML('beforeend', blockHtml);
    });
}

function escapeHtml(str) {
    return String(str)
      .replace(/&/g, '&amp;')
      .replace(/</g, '&lt;')
      .replace(/>/g, '&gt;')
      .replace(/"/g, '&quot;')
      .replace(/'/g, '&#039;');
}

// 여기서부터 "저장" 관련 로직
function onClickSave() {
    if (!currentPageData) {
      alert('먼저 섹션 데이터를 불러와주세요.');
      return;
    }
    if (!window.P1Template || typeof window.P1Template.saveTemplate !== 'function') {
      alert('저장 기능을 사용할 수 없습니다.(브릿지 미연결)');
      return;
    }

    const page = collectPageForSave();
    const json = JSON.stringify(page);

    // 네이티브 브릿지 호출
    P1Template.saveTemplate(json);
}

function collectPageForSave() {
    // 1) 대항목 섹션
    const oldSection = currentPageData.section || {};

    const section = {
      id: oldSection.id, // PK는 그대로 유지
      sectionNo: oldSection.sectionNo, // 또는 parseInt(document.getElementById('sectionNo').textContent, 10)
      category: document.getElementById('category').value.trim(),
      mainQuestion: document.getElementById('main_question').value.trim(),
      target: document.getElementById('target').value.trim(),
      description: document.getElementById('description').value.trim(),
      referenceBasis: document.getElementById('reference_basis').value.trim(),
      useYn: oldSection.useYn ?? 1,
      sortOrder: oldSection.sortOrder ?? oldSection.sectionNo
    };

    // 2) 하위항목들
    const originalItems = currentPageData.items || [];
    // 원본을 건드리지 않도록 복사
    const items = originalItems.map(item => ({ ...item }));

    const inputs = document.querySelectorAll('input.change_input[data-item-index][data-field]');
    inputs.forEach(input => {
      const idx = parseInt(input.dataset.itemIndex, 10);
      const field = input.dataset.field;
      if (!isNaN(idx) && items[idx]) {
        items[idx][field] = input.value.trim();
      }
    });

    return { section, items };
}

  // 네이티브에서 저장 성공/실패 콜백
window.onP1TemplateSaved = function () {
    alert('저장되었습니다.');
    // 여기서 "변경됨" 플래그를 false로 만들고,
    // 페이지 이동 시 경고 안 띄워도 되도록 처리할 수도 있음.
};

window.onP1TemplateSaveFailed = function (msg) {
    alert('저장 실패: ' + (msg || '알 수 없는 오류'));
};