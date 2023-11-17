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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService service;

    @PostMapping("add")
    public ResponseEntity add(@RequestBody Board board,
                              @SessionAttribute(value = "login", required = false) Member login) {

        if(login == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!service.validate(board)){
            return ResponseEntity.badRequest().build();
        }


        if(service.save(board, login)) {
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.internalServerError().build();
        }
    }

    // /api/boardList?p=6
    @GetMapping("list")
    public List<Board> list(@RequestParam(value = "p",defaultValue = "1") Integer page) {
        return service.list(page);
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
    public ResponseEntity edit(@RequestBody Board board,
                               @SessionAttribute(value = "login",required = false) Member login) {
        if(login == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); //401
        }

        if(!service.hasAccess(board.getId(),login)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403
        }


//        System.out.println("board = " + board);
        if (service.validate(board)) {
            if (service.update(board)){
                return ResponseEntity.ok().build();
            }else {
                return ResponseEntity.internalServerError().build();
            }

        }else {
            return ResponseEntity.badRequest().build();
        }
    }

}

