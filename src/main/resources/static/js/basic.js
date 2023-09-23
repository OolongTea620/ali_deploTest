let isLoggingOut = false;  // 로그아웃 중인지 확인하는 전역 플래그

function logout() {
    isLoggingOut = true;  // 로그아웃 중임을 표시
    localStorage.removeItem("Access_Token");
    localStorage.removeItem("Refresh_Token");
    location.reload();
}

window.onload = function() {
    if (isLoggingOut) {
        return;  // 로그아웃 중일 경우, 나머지 로직을 건너뛴다.
    }

    const token = localStorage.getItem('Access_Token');
    const currentPath = window.location.pathname;

    if (!token) {
        if (currentPath === '/store' || currentPath === '/seller') {
            alert("권한이 없습니다.");
            window.location.href = '/';
            return;
        }

        const loggedOutButtons = document.getElementById('loggedOutButtons');
        loggedOutButtons.style.display = 'block';
        document.getElementById('productManagement').style.display = 'none';
        document.getElementById('storeManagement').style.display = 'none';
    } else {
        const decodedToken = atob(token.split('.')[1]);
        const payload = JSON.parse(decodedToken);

        if (payload.userType !== 'SELLER' && (currentPath === '/store' || currentPath === '/seller')) {
            alert("권한이 없습니다.");
            window.location.href = '/';
            return;
        }

        if (payload.userType !== 'SELLER') {
            document.getElementById('productManagement').style.display = 'none';
            document.getElementById('storeManagement').style.display = 'none';
        }

        const username = payload.sub;
        const userType = payload.userType;

        const greetingMessage = `[${userType}] ${username}  `;
        const usernameSpan = document.getElementById('usernameSpan');
        usernameSpan.textContent = greetingMessage;

        const loggedOutButtons = document.getElementById('loggedOutButtons');
        loggedOutButtons.style.display = 'none';
        document.getElementById('loggedInInfo').style.display = 'block';
    }
}