package com.example.prj1be.mapper;

import com.example.prj1be.domain.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {

    @Insert("""
        INSERT INTO member(id,password, email)
        VALUE (#{id},#{password},#{email})
        """)
    int insert(Member member);

    @Select("""
SELECT  id FROM member
WHERE id = #{id}
""")
    String selectId(String id);
}
