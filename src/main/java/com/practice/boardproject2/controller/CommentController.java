package com.practice.boardproject2.controller;

import com.practice.boardproject2.dto.CommentRequestDTO;
import com.practice.boardproject2.dto.CommentResponseDTO;
import com.practice.boardproject2.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * @methodName: comSave
     * @Description: 댓글 저장하기
     * @param commentRequestDTO
     */
    @PostMapping("/comSave")
    public ResponseEntity<CommentResponseDTO> comSave(@RequestBody CommentRequestDTO commentRequestDTO) {
        commentRequestDTO.setRegDate(new Date());
        return new ResponseEntity<>(commentService.comSave(commentRequestDTO), HttpStatus.OK) ;
    }


    /**
     * @methodName: myCommentList
     * @Description: 댓글 목록 가져오기
     * @param boardNum
     */
    @GetMapping("/comList/{boardNum}")
    public ResponseEntity<List<CommentResponseDTO>> myCommentList(@PathVariable("boardNum") Integer boardNum) {
        List<CommentResponseDTO> myCommentList = commentService.getMyComments(boardNum);
        return new ResponseEntity<>(myCommentList, HttpStatus.OK);
    }


    /**
     * @methodName: comDelete
     * @Description: 댓글 삭제하기
     * @param comNum
     */
    @GetMapping("/comDelete")
    public ResponseEntity comDelete(@RequestParam(value = "comNum", required = false) Integer comNum) {
        System.out.println("컴넘?"+comNum);
        commentService.comDelete(comNum);
        return new ResponseEntity(HttpStatus.OK);
    }


    /**
     * @methodName: comModify
     * @Description: 댓글 수정하기
     * @param comNum
     * @param commentRequestDTO
     */
    @PutMapping("/comModify/{comNum}")
    public ResponseEntity<CommentResponseDTO> comModify(@PathVariable("comNum") Integer comNum,
                                                        @RequestBody CommentRequestDTO commentRequestDTO) {
        commentRequestDTO.setComNum(comNum);
        return new ResponseEntity<>(commentService.comModify(commentRequestDTO), HttpStatus.OK);
    }
}
