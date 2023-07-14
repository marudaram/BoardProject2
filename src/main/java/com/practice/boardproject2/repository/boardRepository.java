package com.practice.boardproject2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.practice.boardproject2.entity.board;
@Repository
public interface boardRepository extends JpaRepository<board, Integer> {
}
