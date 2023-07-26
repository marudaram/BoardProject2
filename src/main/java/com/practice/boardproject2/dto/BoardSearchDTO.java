package com.practice.boardproject2.dto;

import com.practice.boardproject2.enums.SearchOption;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BoardSearchDTO {
    @Builder.Default
    private int page = 0;
    @Builder.Default
    private int amount = 10;
    @Builder.Default
    private String keyword = "";
    @Builder.Default
    private SearchOption searchOption = SearchOption.CONTENT;


}
