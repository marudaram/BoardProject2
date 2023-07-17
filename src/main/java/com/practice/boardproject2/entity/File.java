package com.practice.boardproject2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "file")
public class File {

    @Id
    private String fileUuid;

    @Column(length = 100)
    private String fileName;

    @Column(length = 200)
    private String filePath;

    @Column
    private Integer boardNum;

    @Column(length = 30, nullable = false)
    private String writer;
}
