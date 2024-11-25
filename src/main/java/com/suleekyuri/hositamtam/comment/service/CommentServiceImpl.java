package com.suleekyuri.hositamtam.comment.service;

import com.suleekyuri.hositamtam.comment.Comment;
import com.suleekyuri.hositamtam.comment.dto.CommentDto;
import com.suleekyuri.hositamtam.comment.mapper.CommentMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    @Autowired
    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    public void createComment(CommentDto commentDto, Long userId, String aptId) {
        // 댓글 작성: aptId를 전달하여 생성
        Comment comment = commentDto.toEntity(userId, aptId);  // aptId를 toEntity에 전달
        commentMapper.createComment(comment);
    }

    @Override
    public void likeComment(Long commentId, Long userId) {
        // 댓글 존재 여부 확인
        Comment comment = commentMapper.getCommentById(commentId);
        if (comment == null) {
            throw new IllegalArgumentException("댓글이 존재하지 않습니다.");  // 댓글이 존재하지 않으면 예외 발생
        }

        // 댓글 좋아요 추가
        // 좋아요가 이미 눌렸는지 확인
        int existingLikes = commentMapper.checkIfLiked(commentId, userId);
        if (existingLikes == 0) {
            // 좋아요가 아직 눌리지 않았다면
            commentMapper.likeComment(commentId, userId);  // 좋아요 추가
            commentMapper.incrementLikes(commentId);  // 댓글의 좋아요 수 증가
        }
    }

    @Override
    public void cancelLikeComment(Long commentId, Long userId) {
        // 댓글 존재 여부 확인
        Comment comment = commentMapper.getCommentById(commentId);
        if (comment == null) {
            throw new IllegalArgumentException("댓글이 존재하지 않습니다.");  // 댓글이 존재하지 않으면 예외 발생
        }

        // 댓글 좋아요 취소
        // 좋아요가 눌려있는지 확인
        int existingLikes = commentMapper.checkIfLiked(commentId, userId);
        if (existingLikes > 0) {
            // 좋아요가 눌려 있으면
            commentMapper.cancelLikeComment(commentId, userId);  // 좋아요 취소
            commentMapper.decrementLikes(commentId);  // 댓글의 좋아요 수 감소
        }
    }

    @Override
    public List<CommentDto> getCommentsByAptId(String aptId) {
        // 댓글 목록 조회
        List<Comment> comments = commentMapper.getCommentsByAptId(aptId);
        return comments.stream()
                .map(CommentDto::fromEntity)  // Comment 엔티티를 CommentDto로 변환
                .collect(Collectors.toList());
    }
}