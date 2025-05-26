async function login_hash(){
    const id = document.getElementById('username').value;
    const pw = document.getElementById('password').value;
    const hashedPW = await hashMaker(pw);

    // 해싱 없이 원문 그대로 테스트할 땐 위의 3줄 주석처리
    // id = "admin"
    // hashedPW = "1234"

    const response = await fetch('http://localhost:8080/api/login',
                                {
                                method: 'POST',
                                headers: {'Content-Type': 'application/json'},
                                body: JSON.stringify({
                                    username: id,
                                    password: hashedPW
                                })
                                });

    const result = await response.json();
    alert(result.message);
}

// await 하려면 async 있어야함
async function hashMaker(pw){
    const encoder = new TextEncoder();
    const data = encoder.encode(pw);
    const hashBuffer = await crypto.subtle.digest('SHA-256', data);
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    const hashHex = hashArray.map(b => b.toString(16).padStart(2, '0')).join('');
    return hashHex.toUpperCase();   // DB에는 대문자로 저장되어있음
}