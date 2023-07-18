package com.practice.boardproject2.controller;

import com.practice.boardproject2.dto.CommentRequestDTO;
import com.practice.boardproject2.dto.CommentResponseDTO;
import com.practice.boardproject2.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //댓글 저장
    @PostMapping("/comSave")
    public CommentResponseDTO comSave(@RequestBody CommentRequestDTO commentRequestDTO) {
        commentRequestDTO.setRegDate(new Date());
        return commentService.comSave(commentRequestDTO);
    }

    //댓글 리스트
    @GetMapping("/comList/{boardNum}")
    public List<CommentResponseDTO> myCommentList(@PathVariable("boardNum") Integer boardNum) {
        List<CommentResponseDTO> myCommentList = commentService.getMyComments(boardNum);
        return myCommentList;
    }

}
