package com.practice.boardproject2.controller;

import com.practice.boardproject2.dto.BoardRequestDTO;
import com.practice.boardproject2.dto.BoardResponseDTO;
import com.practice.boardproject2.dto.BoardSearchDTO;
import com.practice.boardproject2.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    //게시글 저장
    @PostMapping(value = "/save")
    public BoardResponseDTO save(@RequestBody BoardRequestDTO boardRequestDTO) {
        Date date = new Date();
        boardRequestDTO.setRegDate(date);
        return boardService.save(boardRequestDTO);
    }

    //게시판 리스트 -restcontroller확인
    @GetMapping("/list")
    public Page<BoardResponseDTO> list(BoardSearchDTO param) {
        Page<BoardResponseDTO> boardList =  boardService.getBoardList(param);
        return boardList;
    }

    //게시글 디테일
    @GetMapping(value = "/detail/{boardNum}")
    public ResponseEntity<BoardResponseDTO> read(@PathVariable("boardNum") Integer boardNum) {
        boardService.increaseHit(boardNum);
        return new ResponseEntity<>(boardService.read(boardNum), HttpStatus.OK);
    }

    //게시글 수정하기
    @PutMapping("/detail/{boardNum}")
    public ResponseEntity<BoardResponseDTO> modify(@PathVariable("boardNum") Integer boardNum,
                                                   @RequestBody BoardRequestDTO boardRequestDTO) {
        boardRequestDTO.setBoardNum(boardNum);
        return new ResponseEntity<>(boardService.modify(boardRequestDTO), HttpStatus.OK);
    }

    //게시글 삭제하기
    @PostMapping(value = "/detail/{boardNum}")
    public void delete(@PathVariable("boardNum") Integer boardNum) {
        boardService.delete(boardNum);
    }

    //내가 쓴 게시글 불러오기
    @GetMapping("/myBoard/{id}")
    public List<BoardResponseDTO> myBoardList(@PathVariable("id") String id) {
        List<BoardResponseDTO> myBoardList = boardService.getMyBoardList(id);
        return myBoardList;
    }




}
