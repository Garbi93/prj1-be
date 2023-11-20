package com.example.prj1be.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {

    @Insert("""
        INSERT INTO boardFile (boardId, name)
        VALUES (#{boardId}, #{name})
        """)
    void insert(Integer boardId, String name);
}
