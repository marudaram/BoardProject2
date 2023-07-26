package com.practice.boardproject2.controller;

import com.practice.boardproject2.dto.BoardRequestDTO;
import com.practice.boardproject2.dto.BoardResponseDTO;
import com.practice.boardproject2.dto.BoardSearchDTO;
import com.practice.boardproject2.dto.CommentResponseDTO;
import com.practice.boardproject2.pagination.Criteria;
import com.practice.boardproject2.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    //게시글 저장
    @PostMapping(value = "/save")
    public @ResponseBody ResponseEntity<BoardResponseDTO> save(@RequestBody BoardRequestDTO boardRequestDTO) {
        Date date = new Date();
        boardRequestDTO.setRegDate(date);
        return new ResponseEntity<>(boardService.save(boardRequestDTO), HttpStatus.OK);
    }

    //게시판 리스트 -restcontroller확인
    @GetMapping("/list")
    public @ResponseBody ResponseEntity<Page<BoardResponseDTO>> list(BoardSearchDTO param) {
        Page<BoardResponseDTO> boardList =  boardService.getBoardList(param);
        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }


    //게시글 디테일
    @GetMapping(value = "/detail/{boardNum}")
    public @ResponseBody ResponseEntity<BoardResponseDTO> read(@PathVariable("boardNum") Integer boardNum) {

        return new ResponseEntity<>(boardService.read(boardNum), HttpStatus.OK);
    }

    //게시글 수정하기
    @PutMapping("/detail/{boardNum}")
    public @ResponseBody ResponseEntity<BoardResponseDTO> modify(@PathVariable("boardNum") Integer boardNum,
                                                   @RequestBody BoardRequestDTO boardRequestDTO) {
        boardRequestDTO.setBoardNum(boardNum);
        return new ResponseEntity<>(boardService.modify(boardRequestDTO), HttpStatus.OK);
    }

    //게시글 삭제하기
    @DeleteMapping(value = "/detail/{boardNum}")
    public ResponseEntity delete(@PathVariable("boardNum") Integer boardNum) {
        boardService.delete(boardNum);
        return new ResponseEntity(HttpStatus.OK);
    }

    //내가 쓴 게시글 불러오기
    @GetMapping("/myBoard/{id}")
    public @ResponseBody ResponseEntity<Page<BoardResponseDTO>> myBoardList(@PathVariable("id") String id, BoardSearchDTO param) {
        Page<BoardResponseDTO> myBoardList = boardService.getMyBoardList(id, param);
        return new ResponseEntity<>(myBoardList, HttpStatus.OK);
    }





}
