document.addEventListener('DOMContentLoaded', () => {
    const $title       = document.getElementById('title');
    const $inspectedAt = document.getElementById('inspectedAt'); // type="date"
    const $systemName  = document.getElementById('systemName');
    const $inspector   = document.getElementById('inspector');
    const $department  = document.getElementById('department');
    const $btnSave     = document.getElementById('btnSave');

    // 점검일시 기본값: 오늘 날짜로 세팅 (YYYY-MM-DD)
    if ($inspectedAt && !$inspectedAt.value) {
        const now = new Date();
        const yyyy = now.getFullYear();
        const MM = String(now.getMonth() + 1).padStart(2, '0');
        const dd = String(now.getDate()).padStart(2, '0');
        $inspectedAt.value = `${yyyy}-${MM}-${dd}`;
    }

    $btnSave.addEventListener('click', () => {
        const title       = $title.value.trim();
        const inspectedAt = $inspectedAt.value.trim();   // "2025-11-21"
        const systemName  = $systemName.value.trim();
        const inspector   = $inspector.value.trim();
        const department  = $department.value.trim();

        if (!title) {
            alert('제목은 필수입니다.');
            $title.focus();
            return;
        }

        const payload = {
            title: title,
            inspectedAt: inspectedAt,
            systemName: systemName,
            inspector: inspector,
            department: department
        };

        try {
            // 안드로이드 브릿지 호출 (MainActivity에서 "save"로 등록했음)
            window.save.saveChecklistHeader(JSON.stringify(payload));
        } catch (e) {
            console.error(e);
            alert('앱과 통신 중 오류가 발생했습니다.');
        }
    });
});
