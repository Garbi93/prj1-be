package com.example.prj1be.controller;

import com.example.prj1be.domain.Like;
import com.example.prj1be.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeController {
    private final LikeService service;

    @PutMapping
    public void like(@RequestBody Like like) {
        service.update(like);
    }



}
