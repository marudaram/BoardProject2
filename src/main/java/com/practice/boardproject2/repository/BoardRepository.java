package com.practice.boardproject2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.practice.boardproject2.entity.Board;
@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    Page<Board> findAll(Pageable pageable);

    @Modifying
    @Query(value = "update Board b set b.hit=b.hit+1 where b.boardNum=:boardNum")
    void increaseHit(@Param("boardNum") Integer boardNum);

}
