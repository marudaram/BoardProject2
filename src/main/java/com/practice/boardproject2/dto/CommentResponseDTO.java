package com.practice.boardproject2.dto;

import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentResponseDTO {

    private Integer comNum;
    private String id;
    private String comWriter;
    private String comContent;
    private Date regDate;
    private Integer boardNum;

}
