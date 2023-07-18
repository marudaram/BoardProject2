package com.practice.boardproject2.controller;

import com.practice.boardproject2.dto.BoardRequestDTO;
import com.practice.boardproject2.dto.BoardResponseDTO;
import com.practice.boardproject2.entity.Board;
import com.practice.boardproject2.pagination.Criteria;
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
        boardRequestDTO.setRegDate(new Date());
        return boardService.save(boardRequestDTO);
    }

    //게시판 리스트
    @GetMapping("/list")
    public Page<BoardResponseDTO> list(Criteria cri) {
        //총 개수
        Page<BoardResponseDTO> boardList =  boardService.getBoardList(cri);
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
    public Page<BoardResponseDTO> myBoardList(Criteria cri) {
        Page<BoardResponseDTO> myBoardList = boardService.getMyBoardList(cri);
        return myBoardList;
    }


}
