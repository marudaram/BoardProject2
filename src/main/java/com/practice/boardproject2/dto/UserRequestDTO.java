package com.practice.boardproject2.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequestDTO {

    private String id;
    private String password;
    private String name;
}
