package com.suleekyuri.hositamtam.comment.service;


import com.suleekyuri.hositamtam.comment.dto.CommentDto;
import java.util.List;

public interface CommentService {
    // 댓글 작성
    void createComment(CommentDto commentDto, Long userId, String aptId); // aptId 추가

    // 댓글 좋아요
    void likeComment(Long commentId, Long userId);

    // 댓글 좋아요 취소
    void cancelLikeComment(Long commentId, Long userId);

    // 특정 게시글의 댓글 목록 조회
    List<CommentDto> getCommentsByAptId(String aptId);
}
