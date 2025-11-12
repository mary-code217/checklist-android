function doLogin() {
    const id = document.getElementById('uid').value.trim();
    const pw = document.getElementById('pw').value;
    if(!id || !pw) return showMsg("아이디와 비밀번호를 입력하세요.");

    // 네이티브 브릿지 호출
    Auth.login(id, pw);
}

function showMsg(t) {
    document.getElementById('msg').textContent = t;
}

window.onLoginResult = ok => {
    if(!ok) {
        showMsg("입력하신 정보가 올바르지 않습니다.");
        return
    }

    location.href = 'menu.html';
};