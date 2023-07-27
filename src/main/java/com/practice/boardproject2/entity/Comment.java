package com.practice.boardproject2.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(length = 30)
    private String id;

    // 게시글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_num")
    private Board board;

    @Column(length = 30)
    private String comWriter;

    @Column(length = 1000)
    private String comContent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @CreationTimestamp
    private Date regDate;

    @Column(columnDefinition = "int default 0")
    private int writtenComment;

    //댓글 수정하기
    public void changeComment(String comContent) {
        this.comContent = comContent;
        this.regDate = new Date();
    }


}
