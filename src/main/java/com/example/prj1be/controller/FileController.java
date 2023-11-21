package com.example.prj1be.controller;

import com.example.prj1be.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {
    private final FileService service;

    @DeleteMapping("/remove")
    public void remove(@RequestParam("id") Integer id,
                       @RequestParam("fi") Integer fileId) {
        service.remove(id, fileId);
    }
}
