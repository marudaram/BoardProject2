package com.practice.boardproject2.dto;

import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BoardResponseDTO {

    private Integer boardNum;
    private String id;
    private String title;
    private String content;
    private Date regDate;
    private Integer hit;
}
