package com.practice.boardproject2.repository.spec;

import com.practice.boardproject2.entity.Board;
import org.springframework.data.jpa.domain.Specification;

public class BoardSpecification {

    //제목으로 검색
    public static Specification<Board> withTitle(String title) {
        return (root, query, builder) -> builder.like(root.get("title"), "%" + title + "%");
    }
    //내용으로 검색
    public static Specification<Board> withContent(String content) {
        return (root, query, builder) -> builder.like(root.get("content"), "%" + content + "%");
    }
    //작성자 아이디로 검색
    public static Specification<Board> withId(String id) {
        return (root, query, builder) -> builder.like(root.get("id"), "%" + id + "%");
    }
}

