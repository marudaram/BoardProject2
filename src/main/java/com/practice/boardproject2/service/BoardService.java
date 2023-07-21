package com.practice.boardproject2.service;

import com.practice.boardproject2.dto.BoardRequestDTO;
import com.practice.boardproject2.dto.BoardResponseDTO;
import com.practice.boardproject2.dto.BoardSearchDTO;
import com.practice.boardproject2.entity.Board;
import com.practice.boardproject2.pagination.Criteria;
import com.practice.boardproject2.repository.BoardRepository;
import com.practice.boardproject2.repository.spec.BoardSpecification;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    //게시글 저장하기
    @Transactional
    public BoardResponseDTO save(BoardRequestDTO boardRequestDTO) {
        Board board = boardRepository.save(toEntity(boardRequestDTO));
        return toDto(board);
    }

    //게시글 리스트 가져오기
    @Transactional
    public Page<BoardResponseDTO> getBoardList(BoardSearchDTO param) {

        PageRequest pageRequest = PageRequest.of(param.getPage(), param.getAmount(), Sort.by("boardNum").descending());

        Specification<Board> spec = null;
        switch (param.getSearchOption()) {
            case TITLE:
                spec = BoardSpecification.withTitle(param.getKeyword());
                break;
            case CONTENT:
                spec = BoardSpecification.withContent(param.getKeyword());
                break;
            case ID:
                spec = BoardSpecification.withId(param.getKeyword());
                break;
            default:
                spec = null;
        }

        Page<Board> page = spec == null ?
                boardRepository.findAll(pageRequest) :
                boardRepository.findAll(spec, pageRequest);
        return new PageImpl<>(page.map(this::toDto).toList(), page.getPageable(), page.getTotalElements());

    }

    //게시글 디테일
    @Transactional
    public BoardResponseDTO read(Integer boardNum) {
        Board board = boardRepository.findById(boardNum).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        return toDto(board);
    }

    //조회수 증가 - 디테일에 병합
    @Modifying
    @Transactional
    public void increaseHit(Integer boardNum) {
        boardRepository.increaseHit(boardNum);
    }

    //게시글 수정하기 권한체크
    @Transactional
    public BoardResponseDTO modify(BoardRequestDTO boardRequestDTO) {
        Board board = boardRepository.findById(boardRequestDTO.getBoardNum())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));
        board.changeBoard(boardRequestDTO.getTitle(), boardRequestDTO.getContent());
        return toDto(board);
    }

    //게시글 삭제하기 권한체크
    @Transactional
    public void delete(Integer boardNum) {
        boardRepository.deleteById(boardNum);
    }

    //내가 쓴 게시글 가져오기
    @Transactional
    public Page<BoardResponseDTO> getMyBoardList(String id, Criteria cri) {
        PageRequest pageRequest = PageRequest.of(cri.getPage(), cri.getAmount(), Sort.by("boardNum").descending());
        Page<Board> myBoardPage = boardRepository.findAllById(id, pageRequest);
        List<BoardResponseDTO> dtoList = myBoardPage.stream().map(this::toDto).collect(Collectors.toList());
        return new PageImpl<>(dtoList, myBoardPage.getPageable(), myBoardPage.getTotalElements());
    }

    @Transactional
    public void deleteMine(Integer boardNum) {boardRepository.deleteById(boardNum);}


    private BoardResponseDTO toDto(Board board) {
        return BoardResponseDTO.builder()
                .boardNum(board.getBoardNum())
                .title(board.getTitle())
                .content(board.getContent())
                .hit(board.getHit())
                .id(board.getId())
                .regDate(board.getRegDate())
                .build();
    }


    private Board toEntity(BoardRequestDTO dto) {
        return Board.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .regDate(dto.getRegDate())
                .hit(dto.getHit())
                .build();
    }


}
