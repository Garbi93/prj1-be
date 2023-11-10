package com.example.prj1be.mapper;

import com.example.prj1be.domain.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    @Insert("""
        INSERT INTO member(id,password, email)
        VALUE (#{id},#{password},#{email})
        """)
    int insert(Member member);
}
