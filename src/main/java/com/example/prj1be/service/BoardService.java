package com.example.prj1be.service;

import com.example.prj1be.domain.Board;
import com.example.prj1be.domain.Comment;
import com.example.prj1be.domain.Member;
import com.example.prj1be.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper mapper;
    private final MemberService memberService;
    private final CommentService commentService;

    public boolean save(Board board, Member login) {
        board.setWriter(login.getId());
        return mapper.insert(board) == 1;
    }

    public boolean validate(Board board) {
        if(board == null){
            return false;
        }
        if(board.getContent() == null || board.getContent().isBlank()){
            return false;
        }

        if (board.getTitle() == null || board.getTitle().isBlank()){
            return false;
        }

        return true;
    }

    public List<Board> list() {
        return mapper.selectAll();
    }

    public Board get(Integer id) {
        return mapper.selectById(id);
    }

    public boolean remove(Integer id) {
        commentService.removeByBoardId(id);
            return mapper.deleteById(id) == 1;


    }

    public boolean update(Board board) {
        return mapper.update(board) == 1;
    }

    public boolean hasAccess(Integer id, Member login) {
        // admin 인지 먼저 확인 후
        if(memberService.isAdmin(login)) {
            return true;
        }
        // 어드민이 아니면 자신이 맞는지 확인
        Board board = mapper.selectById(id);
        return board.getWriter().equals(login.getId()); //  작성자와 로그인한 사람이 같은지 확인
    }






}
