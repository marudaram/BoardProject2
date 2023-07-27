package com.practice.boardproject2.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Column(columnDefinition = "TEXT") //type을 text로 바꿔준다
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @CreationTimestamp
    private Date regDate;

    @Column(columnDefinition = "int default 0")
    private Integer hit;

    // 게시글의 댓글 목록
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board")
    private List<Comment> commentList = new ArrayList<>();

    //게시글 수정
    public void changeBoard(String title, String content) {
        this.title = title;
        this.content = content;
        this.regDate = new Date();
    }

    //조회수 증가
    public int increaseHit() {
        return this.hit++;
    }

}