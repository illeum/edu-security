document.addEventListener('DOMContentLoaded', () => {
    const headerRight = document.getElementById('headerRight');
    const token = localStorage.getItem('jwtToken');

    if (token) {
        fetch('/api/me', {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        })
        .then(res => {
            if (!res.ok) throw new Error();
            return res.json();
        })
        .then(data => {
            if (data.username) {
                headerRight.innerHTML = `
                    <span>${data.username}님, 안녕하세요.</span>
                    <button id="logoutBtn" style="margin-left: 10px;">로그아웃</button>
                `;
                document.getElementById('logoutBtn').addEventListener('click', () => {
                    localStorage.removeItem('jwtToken');
                    window.location.href = '/page/login.html';
                });
            } else {
                headerRight.innerHTML = `<a href="/page/login.html">로그인</a>`;
            }
        })
        .catch(() => {
            localStorage.removeItem('jwtToken');
            headerRight.innerHTML = `<a href="/page/login.html">로그인</a>`;
        });
    } else {
        headerRight.innerHTML = `<a href="/page/login.html">로그인</a>`;
    }
});
