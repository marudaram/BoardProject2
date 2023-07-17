package com.practice.boardproject2.pagination;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Criteria {

    private int page; //페이지번호 - 프론트에서 보내줘야함
    private int amount; //데이터개수

}
