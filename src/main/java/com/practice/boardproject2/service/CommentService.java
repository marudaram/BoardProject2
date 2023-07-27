package com.practice.boardproject2.service;

import com.practice.boardproject2.dto.BoardResponseDTO;
import com.practice.boardproject2.dto.CommentRequestDTO;
import com.practice.boardproject2.dto.CommentResponseDTO;
import com.practice.boardproject2.entity.Board;
import com.practice.boardproject2.entity.Comment;
import com.practice.boardproject2.repository.BoardRepository;
import com.practice.boardproject2.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    //댓글 저장하기
    @Transactional
    public CommentResponseDTO comSave(CommentRequestDTO commentRequestDTO) {
        Comment comment = commentRepository.save(toEntity((commentRequestDTO)));
        return toDto(comment);
    }


    //댓글 가져오기
    @Transactional
    public List<CommentResponseDTO> getMyComments(Integer boardNum) {
        List<Comment> commentList = commentRepository.findByBoardNum(boardNum);
        List<CommentResponseDTO> commentResponseDTOList = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentResponseDTO commentResponseDTO = CommentResponseDTO.builder()
                    .comNum(comment.getComNum())
                    .comWriter(comment.getComWriter())
                    .comContent(comment.getComContent())
                    .regDate(comment.getRegDate())
                    .boardNum(comment.getBoard().getBoardNum())
                    .id(comment.getId())
                    .build();
            commentResponseDTOList.add(commentResponseDTO);
        }
        return commentResponseDTOList;
    }


    //댓글 삭제하기
    @Transactional
    public void comDelete(Integer comNum) {
        commentRepository.comDelete(comNum);
    }


    //댓글 수정하기
    @Transactional
    public CommentResponseDTO comModify(CommentRequestDTO commentRequestDTO) {
        Comment comment = commentRepository.findById(commentRequestDTO.getComNum())
                .orElseThrow(() -> new IllegalArgumentException("댓글을 수정할 수 없습니다."));
        comment.changeComment(commentRequestDTO.getComContent());
        return toDto(comment);
    }


    private CommentResponseDTO toDto(Comment comment) {
        return CommentResponseDTO.builder()
                .comNum(comment.getComNum())
                .id(comment.getId())
                .comWriter(comment.getComWriter())
                .comContent(comment.getComContent())
                .regDate(comment.getRegDate())
                .boardNum(comment.getBoard().getBoardNum())
                .build();

    }


    private Comment toEntity(CommentRequestDTO dto) {
        Board board = boardRepository.findById(dto.getBoardNum()).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다."));
        return Comment.builder()
                .comNum(dto.getComNum())
                .id(dto.getId())
                .comWriter(dto.getComWriter())
                .comContent(dto.getComContent())
                .regDate(dto.getRegDate())
                .board(board)
                .build();
    }
}
