package com.practice.boardproject2.controller;

import com.practice.boardproject2.dto.BoardRequestDTO;
import com.practice.boardproject2.dto.BoardResponseDTO;
import com.practice.boardproject2.dto.BoardSearchDTO;
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

    /**
     * @methodName: save
     * @Description: 게시글 저장하기
     * @param boardRequestDTO
     */
    @PostMapping(value = "/save")
    public ResponseEntity<BoardResponseDTO> save(@RequestBody BoardRequestDTO boardRequestDTO) {
        Date date = new Date();
        boardRequestDTO.setRegDate(date);
        return new ResponseEntity<>(boardService.save(boardRequestDTO), HttpStatus.OK);
    }


    /**
     * @methodName: list
     * @Description: 게시판 목록 가져오기
     * @param param
     */
    @GetMapping("/list")
    public ResponseEntity<Page<BoardResponseDTO>> list(BoardSearchDTO param) {
        Page<BoardResponseDTO> boardList =  boardService.getBoardList(param);
        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }


    /**
     * @methodName: read
     * @Description: 게시글 상세보기
     * @param boardNum
     */
    @GetMapping(value = "/detail/{boardNum}")
    public ResponseEntity<BoardResponseDTO> read(@PathVariable("boardNum") Integer boardNum) {

        return new ResponseEntity<>(boardService.read(boardNum), HttpStatus.OK);
    }


    /**
     * @methodName: modify
     * @Description: 게시글 수정하기
     * @param boardNum
     * @param boardRequestDTO
     */
    @PutMapping("/detail/{boardNum}")
    public ResponseEntity<BoardResponseDTO> modify(@PathVariable("boardNum") Integer boardNum,
                                                   @RequestBody BoardRequestDTO boardRequestDTO) {
        boardRequestDTO.setBoardNum(boardNum);
        return new ResponseEntity<>(boardService.modify(boardRequestDTO), HttpStatus.OK);
    }


    /**
     * @methodName: delete
     * @Description: 게시글 삭제하기
     * @param boardNum
     */
    @DeleteMapping(value = "/detail/{boardNum}")
    public ResponseEntity delete(@PathVariable("boardNum") Integer boardNum) {
        boardService.delete(boardNum);
        return new ResponseEntity(HttpStatus.OK);
    }


    /**
     * @methodName: myBoardList
     * @Description: 내가 쓴 게시글 불러오기
     * @param id
     * @param param
     */
    @GetMapping("/myBoard/{id}")
    public ResponseEntity<Page<BoardResponseDTO>> myBoardList(@PathVariable("id") String id, BoardSearchDTO param) {
        Page<BoardResponseDTO> myBoardList = boardService.getMyBoardList(id, param);
        return new ResponseEntity<>(myBoardList, HttpStatus.OK);
    }
}
