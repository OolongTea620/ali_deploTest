window.onload = function() {
    // 1. 로컬 스토리지에서 JWT 토큰을 가져옵니다.
    const token = localStorage.getItem('Access_Token');
    console.log(token);

    if (!token) {
        // 토큰이 없을 경우
        const loggedOutButtons = document.getElementById('loggedOutButtons');
        loggedOutButtons.style.display = 'block';
    } else {
        // 2. JWT 토큰을 디코드하여 userType과 username을 확인합니다.
        const decodedToken = atob(token.split('.')[1]);
        const payload = JSON.parse(decodedToken);

        // 3. userType이 "SELLER"인 경우에만 특정 메뉴를 보여줍니다.
        if (payload.userType !== 'SELLER') {
            document.getElementById('productManagement').style.display = 'none'; // 상품 관리 메뉴 숨기기
            document.getElementById('storeManagement').style.display = 'none';   // 매장 관리 메뉴 숨기기
        }

        // 4. 토큰이 있을 경우 username을 출력합니다.
        const username = payload.username;
        const usernameElement = document.createElement('div');
        usernameElement.textContent = username;
        const headerDiv = document.querySelector('.header .center');
        headerDiv.appendChild(usernameElement);

        // 5. 로그인/회원가입 버튼을 숨깁니다.
        const loggedOutButtons = document.getElementById('loggedOutButtons');
        loggedOutButtons.style.display = 'none';
    }
}