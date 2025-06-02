document.addEventListener('DOMContentLoaded', () => {
    const headerRight = document.getElementById('headerRight');
    const token = localStorage.getItem('jwtToken');

    if (token) {
        fetch('http://localhost:8080/api/me', {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        })
        .then(res => {
            if (!res.ok) throw new Error('토큰 유효성 검사 실패');
            return res.json();
        })
        .then(data => {
            // 서버에서 받은 data는 { username: "실제아이디" }
            const username = data.username || '';

            // 1) “{username}님 안녕하세요.” 텍스트 노드 생성
            const greeting = document.createElement('span');
            greeting.id = 'greeting'
            greeting.textContent = `${username}님 안녕하세요!`;
            greeting.style.marginRight = '12px';   // 여백 조절

            // 2) “로그아웃” 링크 생성
            const logoutLink = document.createElement('a');
            logoutLink.href = '#';
            logoutLink.textContent = 'Logout';
            logoutLink.style.cursor = 'pointer';
            logoutLink.addEventListener('click', (e) => {
                e.preventDefault();
                // 클라이언트에서 JWT 제거
                localStorage.removeItem('jwtToken');
                // 로그인 페이지로 돌아가기
                window.location.href = '/page/project_page.html';
            });

            // 3) headerRight 영역을 지우고, 인사말 + 로그아웃 링크를 붙이기
            headerRight.innerHTML = '';
            headerRight.appendChild(greeting);
            headerRight.appendChild(logoutLink);
        })
        .catch(err => {
            console.error(err);
            // 토큰이 유효하지 않거나 요청 실패 시, JWT 삭제
            localStorage.removeItem('jwtToken');
        });
    }
});