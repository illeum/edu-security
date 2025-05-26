const memoText = document.getElementById('memoText');
const addBtn   = document.getElementById('addBtn');
const clearBtn = document.getElementById('clearBtn');
const memoList = document.getElementById('memoList');

const API_BASE = 'http://localhost:8080/api/memo';

// 메모 추가
addBtn.addEventListener('click', async () => {
    const text = memoText.value.trim();
    if (!text) return;

    const res = await fetch(API_BASE, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ content: text })
    });

    if (!res.ok) {
        alert('메모 추가 실패')
        return;
    }

    const data = await res.json();  // { id, text }
    appendMemo(data.id, data.text);
    memoText.value = '';
});

// 전체 삭제
clearBtn.addEventListener('click', async () => {
    const res = await fetch(API_BASE + "/delete", { method: 'POST' });
    if (res.ok) {
        memoList.innerHTML = '';
    } else {
        alert('전체 삭제 실패')
    }
});

function appendMemo(id, text) {
    const li = document.createElement('li');
    li.className = 'memo-item';
    li.dataset.id = id;

    const span = document.createElement('span');
    span.textContent = text;

    const delBtn = document.createElement('button');
    delBtn.textContent = '삭제';
    delBtn.className = 'memo-del';
    delBtn.addEventListener('click', async () => {
        // 개별 삭제
        const res = await fetch(`${API_BASE}/deleteOne`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({ index: id })
        });

        if (res.ok) {
            li.remove();
        } else {
            alert('삭제 실패')
        }
    });

    li.append(span, delBtn);
    memoList.prepend(li);
}

// 페이지 로드 시 기존 메모 불러오기
window.addEventListener('DOMContentLoaded', async () => {
    const res = await fetch(API_BASE);
    if (!res.ok) {
        alert('메모 리스트 로드 실패')
        return;
    }
    const items = await res.json();  // [{id, text}, ...]
    items.forEach(item => appendMemo(item.id, item.text));
});
