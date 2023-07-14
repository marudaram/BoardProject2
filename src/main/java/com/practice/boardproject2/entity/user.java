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
@Table(name = "user")
public class user {

    @Id
    @Column(length = 30, nullable = false)
    private String id;

    @Column(length = 100)
    private String password;

    @Column(length = 10)
    private String name;


}
