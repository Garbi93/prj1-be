package com.example.prj1be.mapper;

import com.example.prj1be.domain.Like;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LikeMapper {

    @Delete("""
        DELETE FROM boardLike
        WHERE boardId = #{boardId}
        AND memberId = #{memberId}
        """)
    int delete(Like like);

    @Insert("""
        INSERT INTO boardLike (boardId, memberId)
        VALUES (#{boardId}, #{memberId})
        """)
    int insert(Like like);

    @Select("""
        SELECT COUNT(id) FROM boardLike
        WHERE boardId = #{boardId}
        """)
    int countByBoardId(Integer boardId);


    @Select("""
        SELECT * FROM boardLike
        WHERE boardId = #{boardId}
            AND memberId = #{memberId}
        """)
    Like selectByBoardIdAndMemberId(Integer boardId, String memberId);

    @Delete("""
        DELETE FROM boardLike
        WHERE boardId = #{id}
        """)
    void deleteByBoardId(Integer id);

    @Delete("""
        DELETE FROM boardLike
        WHERE id = #{id}
        """)
    void deleteById(Integer id);

    @Select("""
        SELECT id
        FROM boardLike
        WHERE memberId = #{memberId}
        """)
    List<Integer> selectIdLikeByMemberId(String memberId);
}
