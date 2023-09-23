window.onload = function() {
    const token = localStorage.getItem('Access_Token');
    const currentPath = window.location.pathname;

    if (!token) {
        // 현재 엔드포인트가 /store 또는 /seller일 때 토큰이 없으면 리다이렉트
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

        // userType이 SELLER가 아니면서 /store 또는 /seller에 접속한 경우 리다이렉트
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