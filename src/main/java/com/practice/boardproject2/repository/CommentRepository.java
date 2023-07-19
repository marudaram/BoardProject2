package com.practice.boardproject2.repository;

import com.practice.boardproject2.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    //댓글 리스트
    List<Comment> findByBoardNum(@Param("boardNum") Integer boardNum);

    //댓글 삭제하기
    @Modifying
    @Query(value = "delete from Comment c where c.comNum=:comNum")
    void comDelete(@Param("comNum") Integer comNum);

}
