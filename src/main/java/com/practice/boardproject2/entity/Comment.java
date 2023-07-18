package com.practice.boardproject2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comNum;

    @Column
    private Integer boardNum;

    @Column(length = 30)
    private String id;

    @Column(length = 30)
    private String comWriter;

    @Column(length = 1000)
    private String comContent;

    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;

}
