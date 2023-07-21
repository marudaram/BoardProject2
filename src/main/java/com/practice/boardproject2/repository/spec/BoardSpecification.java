package com.practice.boardproject2.repository.spec;

import com.practice.boardproject2.entity.Board;
import org.springframework.data.jpa.domain.Specification;

public class BoardSpecification {

    public static Specification<Board> withTitle(String title) {
        return (root, query, builder) -> builder.like(root.get("title"), title);
    }

    public static Specification<Board> withContent(String content) {
        return (root, query, builder) -> builder.like(root.get("content"), content);
    }

    public static Specification<Board> withId(String id) {
        return (root, query, builder) -> builder.like(root.get("id"), id);
    }


}

