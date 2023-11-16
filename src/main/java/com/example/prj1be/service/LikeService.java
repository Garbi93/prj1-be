package com.example.prj1be.service;

import com.example.prj1be.domain.Like;
import com.example.prj1be.domain.Member;
import com.example.prj1be.mapper.LikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeMapper mapper;

    public void update(Like like, Member login) {
        like.setMemberId(login.getId());

        // 처음 좋아요를 누를 때 : insert
        // 다시 누르면 : delete
        int count = 0;
        if(mapper.delete(like) == 0){
            count = mapper.insert(like);
        }
    }
}
