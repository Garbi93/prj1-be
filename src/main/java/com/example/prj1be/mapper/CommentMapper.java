package com.example.prj1be.mapper;

import com.example.prj1be.domain.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("""
        INSERT INTO  comment(boardId,comment,memberId)
        VALUE (#{boardId}, #{comment},#{memberId})
        """)
    int insert(Comment comment);

    @Select("""
        SELECT * FROM comment
        WHERE boardId = #{boardId}
        """)
    List<Comment> selectAByBoardId(Integer boardId);
}
