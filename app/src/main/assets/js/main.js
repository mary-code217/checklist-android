// 콜백용 네임스페이스
window.checklist = window.checklist || {};

// 현재 페이지 상태
let currentPage = 1;
let totalPages = 1;

// 1) 조회 결과 콜백 (네이티브 → JS)
window.checklist.onFindChecklist = function (result) {
    const tbody = document.getElementById('checklist-body');
    if (!tbody) return;

    tbody.innerHTML = '';

    if (!result || !Array.isArray(result.items)) {
        return;
    }

    // 페이지 상태 업데이트
    currentPage = result.page || 1;
    totalPages = result.totalPages || 1;

    // 본문 행 렌더링
    result.items.forEach(function (item) {
        const tr = document.createElement('tr');

        // 체크박스 (value = id)
        const tdCheckbox = document.createElement('td');
        const cb = document.createElement('input');
        cb.type = 'checkbox';
        cb.className = 'row-check';
        cb.value = item.id;
        tdCheckbox.appendChild(cb);
        tr.appendChild(tdCheckbox);

        // 번호
        const tdNo = document.createElement('td');
        const displayNo = parseInt(item.no, 10);
        tdNo.textContent = isNaN(displayNo) ? item.no : displayNo;
        tr.appendChild(tdNo);

        // 제목 (클릭 시 상세 이동 요청)
        const tdTitle = document.createElement('td');

        const titleLink = document.createElement('a');
        titleLink.href = 'javascript:void(0)';
        titleLink.textContent = item.title;
        titleLink.dataset.id = item.id;  // ← 여기에 PK 심어두기

        titleLink.addEventListener('click', function () {
            const id = parseInt(this.dataset.id, 10);
            if (window.Android && Android.openChecklistDetail) {
                Android.openChecklistDetail(id);   // ← 나중에 detail 구현되면 이 메서드 안에서 detail.html 로드
            }
        });

        tdTitle.appendChild(titleLink);
        tr.appendChild(tdTitle);


        // 상태
        const tdStatus = document.createElement('td');
        tdStatus.textContent = item.statusLabel;
        tr.appendChild(tdStatus);

        // 작성일
        const tdDate = document.createElement('td');
        tdDate.textContent = item.createdDateLabel;
        tr.appendChild(tdDate);

        tbody.appendChild(tr);
    });

    // 페이징 렌더링
    renderPagination();
};

// 2) 삭제 결과 콜백 (네이티브 → JS)
window.checklist.onDeleted = function (result) {
    if (!result) {
        alert('삭제 처리 중 오류가 발생했습니다.');
        return;
    }

    if (result.success) {
        alert(result.message || '삭제가 완료되었습니다.');
        // 현재 페이지 다시 조회
        if (window.Android && Android.findChecklist) {
            Android.findChecklist(currentPage);
        }
    } else {
        alert(result.message || '삭제에 실패했습니다.');
    }
};

// 3) 체크된 id 수집
function collectSelectedIds() {
    const checkboxes = document.querySelectorAll('.row-check:checked');
    const ids = [];
    checkboxes.forEach(cb => {
        const id = parseInt(cb.value, 10);
        if (!isNaN(id)) {
            ids.push(id);
        }
    });
    return ids;
}

// 4) 삭제 버튼 클릭
function onClickDeleteSelected(e) {
    e.preventDefault();

    const ids = collectSelectedIds();
    if (ids.length === 0) {
        alert('삭제할 항목을 선택해주세요.');
        return;
    }

    if (!confirm(ids.length + '건을 삭제하시겠습니까?')) {
        return;
    }

    if (window.Android && Android.deleteChecklist) {
        Android.deleteChecklist(JSON.stringify(ids));
    } else {
        console.log('Android.deleteChecklist 호출 불가', ids);
    }
}

// 5) 전체 선택 체크박스
function onChangeCheckAll(e) {
    const checked = e.target.checked;
    const checkboxes = document.querySelectorAll('.row-check');
    checkboxes.forEach(cb => {
        cb.checked = checked;
    });
}

// 6) 페이징 UI 렌더링
function renderPagination() {
    const container = document.getElementById('page-numbers');
    const btnPrev = document.getElementById('btn-prev');
    const btnNext = document.getElementById('btn-next');

    if (!container) return;

    container.innerHTML = '';

    // 페이지 숫자 버튼 생성 (1 ~ totalPages)
    for (let p = 1; p <= totalPages; p++) {
        const a = document.createElement('a');
        a.href = 'javascript:void(0)';
        a.textContent = p;
        a.className = 'pageNo';
        if (p === currentPage) {
            a.classList.add('active');
        }

        a.addEventListener('click', () => {
            if (window.Android && Android.findChecklist) {
                Android.findChecklist(p);
            }
        });

        container.appendChild(a);
    }

    // 이전/다음 버튼 상태 (원하면 CSS에서 .disabled 스타일 지정)
    if (btnPrev) {
        btnPrev.classList.toggle('disabled', currentPage <= 1);
    }
    if (btnNext) {
        btnNext.classList.toggle('disabled', currentPage >= totalPages);
    }
}

// 7) 이전/다음 버튼 클릭
function onClickPrev(e) {
    e.preventDefault();
    if (currentPage <= 1) return;
    if (window.Android && Android.findChecklist) {
        Android.findChecklist(currentPage - 1);
    }
}

function onClickNext(e) {
    e.preventDefault();
    if (currentPage >= totalPages) return;
    if (window.Android && Android.findChecklist) {
        Android.findChecklist(currentPage + 1);
    }
}

// 8) 초기 바인딩 + 첫 페이지 조회
document.addEventListener('DOMContentLoaded', function () {
    const btnDelete = document.getElementById('btn-delete');
    if (btnDelete) {
        btnDelete.addEventListener('click', onClickDeleteSelected);
    }

    const checkAll = document.getElementById('check-all');
    if (checkAll) {
        checkAll.addEventListener('change', onChangeCheckAll);
    }

    const btnPrev = document.getElementById('btn-prev');
    if (btnPrev) {
        btnPrev.addEventListener('click', onClickPrev);
    }

    const btnNext = document.getElementById('btn-next');
    if (btnNext) {
        btnNext.addEventListener('click', onClickNext);
    }

    // 첫 페이지 조회
    if (window.Android && Android.findChecklist) {
        Android.findChecklist(1);
    } else {
        console.log('Android.findChecklist 없음 - 브라우저 테스트 중일 수 있음');
    }
});