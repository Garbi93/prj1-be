package com.example.prj1be.mapper;

import com.example.prj1be.domain.BoardFile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("""
        INSERT INTO boardFile (boardId, name)
        VALUES (#{boardId}, #{name})
        """)
    void insert(Integer boardId, String name);

    @Select("""
        SELECT id, name FROM boardFile
        WHERE boardId = #{boardId}
        """)
    List<BoardFile> selectNameByBoardId(Integer boardId);
}
