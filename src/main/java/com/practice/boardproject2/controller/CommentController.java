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

    //댓글 저장
    @PostMapping("/comSave")
    public @ResponseBody ResponseEntity<CommentResponseDTO> comSave(@RequestBody CommentRequestDTO commentRequestDTO) {
        commentRequestDTO.setRegDate(new Date());
        return new ResponseEntity<>(commentService.comSave(commentRequestDTO), HttpStatus.OK) ;
    }

    //댓글 리스트
    @GetMapping("/comList/{boardNum}")
    public @ResponseBody ResponseEntity<List<CommentResponseDTO>> myCommentList(@PathVariable("boardNum") Integer boardNum) {
        List<CommentResponseDTO> myCommentList = commentService.getMyComments(boardNum);

        return new ResponseEntity<>(myCommentList, HttpStatus.OK);
    }


    //댓글 삭제하기
    @GetMapping("/comDelete")
    public ResponseEntity comDelete(@RequestParam(value = "comNum", required = false) Integer comNum) {
        System.out.println("컴넘?"+comNum);
        commentService.comDelete(comNum);
        return new ResponseEntity(HttpStatus.OK);
    }

    //댓글 수정하기
    @PutMapping("/comModify/{comNum}")
    public @ResponseBody ResponseEntity<CommentResponseDTO> comModify(@PathVariable("comNum") Integer comNum,
                                                        @RequestBody CommentRequestDTO commentRequestDTO) {

        commentRequestDTO.setComNum(comNum);
        return new ResponseEntity<>(commentService.comModify(commentRequestDTO), HttpStatus.OK);

    }

    //수정한 댓글 가져오기
    @GetMapping("/comDetail")
    public @ResponseBody ResponseEntity<CommentResponseDTO> comDetail(@RequestParam("comNum") Integer comNum) {
        return new ResponseEntity<>(commentService.comDetail(comNum), HttpStatus.OK);
    }

    //댓글 총 개수
//    @PostMapping("/getComTotal/{boardNum}")
//    public ResponseEntity<Integer> getComTotal(@PathVariable("boardNum") Integer boardNum) {
//        return new ResponseEntity<>(commentService.getComTotal(boardNum), HttpStatus.OK);
//    }

}
