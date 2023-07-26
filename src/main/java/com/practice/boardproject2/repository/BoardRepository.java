package com.practice.boardproject2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.practice.boardproject2.entity.Board;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer>, JpaSpecificationExecutor<Board> {

    void deleteById(@PathVariable("boardNum") Integer boardNum);

    Page<Board> findAllById(@PathVariable("id") String id, Pageable pageable);

}
