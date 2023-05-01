package com.example.b2bservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private Long commentNo;
    private Long boardNo;
    private String userId;
    private String contents;
    private String writtenDate;

}
