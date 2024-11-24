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

    // 특정 게시글의 댓글 목록 조회
    List<Comment> getCommentsByPostId(Long postId);
}
