package com.example.prj1be.domain;

import lombok.Data;

@Data
public class Like {
    private Integer id;
    private Integer boardId;
    private String memberId;
}
