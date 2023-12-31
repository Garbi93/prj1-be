package com.example.prj1be.controller;

import com.example.prj1be.domain.Board;
import com.example.prj1be.domain.Member;
import com.example.prj1be.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.logging.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService service;

    @PostMapping("add")
    public ResponseEntity add(Board board,
                              @RequestParam(value = "uploadFiles[]", required = false) MultipartFile[] files,
                              @SessionAttribute(value = "login", required = false) Member login) throws IOException {

        if(login == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!service.validate(board)){
            return ResponseEntity.badRequest().build();
        }


        if(service.save(board, files, login)) {
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.internalServerError().build();
        }
    }

    // /api/boardList?p=6
    // /api/boardList?k=java
    @GetMapping("list")
    public Map<String, Object> list(@RequestParam(value = "p",defaultValue = "1") Integer page,
                                    @RequestParam(value = "k",defaultValue = "") String keyword,
                                    @RequestParam(value = "c",defaultValue = "all")String category) {

        // boardList, List<Board>
        // pageInfo, ...
        // 이런식으로 데이터를 넘길예정
        return service.list(page, keyword, category);
    }

    @GetMapping("id/{id}")
    public Board get(@PathVariable Integer id) {
        return service.get(id);
    }

    @DeleteMapping("remove/{id}")
    public ResponseEntity remove(@PathVariable Integer id,
                                 @SessionAttribute(value = "login",required = false)Member login) {
        if(login == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 로그인을 안해서 권한이 없는 에러
        }

        if(!service.hasAccess(id, login)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 에러 로그인은 했지만 권한이 없는 에러
        }

        if(service.remove(id)) {
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("edit")
    public ResponseEntity edit(Board board,
                               @RequestParam(value = "removeFileIds[]", required = false) List<Integer> removeFileIds,
                               @RequestParam(value = "uploadFiles[]",required = false) MultipartFile[] uploadFiles,
                               @SessionAttribute(value = "login",required = false) Member login) throws IOException {
        if(login == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); //401
        }

        if(!service.hasAccess(board.getId(),login)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403
        }


//        System.out.println("board = " + board);
        if (service.validate(board)) {
            if (service.update(board, removeFileIds, uploadFiles)){
                return ResponseEntity.ok().build();
            }else {
                return ResponseEntity.internalServerError().build();
            }

        }else {
            return ResponseEntity.badRequest().build();
        }
    }

}

