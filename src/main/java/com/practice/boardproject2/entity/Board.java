package com.practice.boardproject2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@DynamicInsert
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boardNum; // pk -> auto_increment

    @Column(length = 30)
    private String id;

    @Column(length = 210)
    private String title;

    @Lob
//    @Column(columnDefinition = "TEXT") //type을 text로 바꿔준다
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;

    @Column(columnDefinition = "int default 0")
    private Integer hit;

    public void changeBoard(String title, String content) {
        this.title = title;
        this.content = content;
        this.regDate = new Date();
    }


}
