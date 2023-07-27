package com.practice.boardproject2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import com.practice.boardproject2.entity.Board;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer>, JpaSpecificationExecutor<Board> {

    @EntityGraph(attributePaths = "commentList", type = EntityGraph.EntityGraphType.LOAD)
    Page<Board> findAll(Pageable pageable);
    @EntityGraph(attributePaths = "commentList", type = EntityGraph.EntityGraphType.LOAD)
    Page<Board> findAll(@Nullable Specification<Board> spec, Pageable pageable);

    void deleteById(@PathVariable("boardNum") Integer boardNum);

    Page<Board> findAllById(@PathVariable("id") String id, Pageable pageable);

}
