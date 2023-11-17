package com.example.prj1be.mapper;

import com.example.prj1be.domain.Board;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapper {

    @Insert("""
        INSERT INTO board (title,content,writer)
        VALUES (#{title}, #{content}, #{writer})
        """)
    Integer insert(Board board);

    @Select("""
            SELECT b.id,
                 b.title,
                 b.writer,
                 m.nickName,
                 b.inserted,
                 COALESCE(comment_count, 0) as countComment,
                 COALESCE(like_count, 0) as countLike
          FROM board b
                   JOIN member m ON b.writer = m.id
                   LEFT JOIN (
              SELECT boardId, COUNT(id) AS comment_count
              FROM comment
              GROUP BY boardId
          ) c ON b.id = c.boardId
                   LEFT JOIN (
              SELECT boardId, COUNT(id) AS like_count
              FROM boardLike
              GROUP BY boardId
          ) bl ON b.id = bl.boardId
          ORDER BY b.id DESC;
        """)
    List<Board> selectAll();

    @Select("""
        SELECT 
                b.id, 
                b.title, 
                b.content, 
                b.writer,
                m.nickName,
                b.inserted
        FROM board b JOIN member m ON b.writer = m.id
        WHERE b.id = #{id}
        """)
    Board selectById(Integer id);

    @Delete("""
        DELETE FROM board
        WHERE id = #{id}
        """)
    int deleteById(Integer id);


    @Update("""
        UPDATE board
        SET title = #{title},
        content = #{content},
        writer = #{writer}
        WHERE id = #{id}
        """)
    int update(Board board);

    @Delete("""
        DELETE FROM board
        WHERE writer = #{writer}
        """)
    int deleteByWriter(String writer);

    @Select("""
        SELECT id
        FROM board
        WHERE writer = #{writer}
        """)
    List<Integer> selectIdListByMemberId(String writer);
}
