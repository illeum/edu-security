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

    @GetMapping
    public List<String> getMemos() {
        return memos;
    }

    @PostMapping
    public String addMemo(@RequestBody Map<String, String> body) {
        String content = body.get("content");
        memos.add(content);  // XSS 발생
        return "ok";
    }

    @PostMapping("/delete")
    public String deleteMemos() {
        memos.clear();
        return "deleted";
    }
}