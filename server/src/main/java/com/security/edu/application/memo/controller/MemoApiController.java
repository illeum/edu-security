package com.security.edu.application.memo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api/memo")
public class MemoApiController {

    private List<String> memos = new ArrayList<>();

    public static class MemoDto {
        private int id;
        private String text;

        public MemoDto(int id, String text) {
            this.id = id;
            this.text = text;
        }
        public int getId() { return id; }
        public String getText() { return text; }
    }

    @GetMapping
    public List<MemoDto> getMemos() {
        List<MemoDto> list = new ArrayList<>();
        for (int i = 0; i < memos.size(); i++) {
            list.add(new MemoDto(i, memos.get(i)));
        }
        return list;
    }

    @PostMapping
    public MemoDto addMemo(@RequestBody Map<String, String> body) {
        String raw = body.get("content");
        memos.add(raw);  // XSS 발생
        int id = memos.size() - 1;
        return new MemoDto(id, raw);
    }

    @PostMapping("/delete")
    public String deleteMemos() {
        memos.clear();
        return "deleted";
    }

    // 개별 삭제
    @PostMapping("/deleteOne")
    public String deleteOne(@RequestBody Map<String, Integer> body) {
        Integer idx = body.get("index");
        if (idx != null && idx >= 0 && idx < memos.size()) {
            memos.remove(idx);
            return "ok";
        } else {
            return "invalid index";
        }
    }
}