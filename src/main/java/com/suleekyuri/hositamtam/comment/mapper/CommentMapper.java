package com.suleekyuri.hositamtam.comment.mapper;

import com.suleekyuri.hositamtam.comment.Comment;
import com.suleekyuri.hositamtam.comment.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    // 댓글 작성
    void createComment(Comment comment);

    // 댓글 좋아요
    void likeComment(Long commentId, Long userId);

    // 댓글 좋아요 취소
    void cancelLikeComment(Long commentId, Long userId);

    // 댓글 좋아요 수 증가
    void incrementLikes(Long commentId);

    // 댓글 좋아요 수 감소
    void decrementLikes(Long commentId);

    // 특정 게시글의 댓글 목록 조회
    List<Comment> getCommentsByAptId(String aptId);

    // 댓글 존재 여부 확인 (댓글 조회)
    Comment getCommentById(Long commentId);

    // 사용자가 댓글에 이미 좋아요를 눌렀는지 확인
    int checkIfLiked(Long commentId, Long userId);
}
