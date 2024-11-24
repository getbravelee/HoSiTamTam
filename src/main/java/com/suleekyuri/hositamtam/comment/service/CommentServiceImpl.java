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
    public void createComment(CommentDto commentDto, Long userId) {
        // 댓글 작성
        Comment comment = commentDto.toEntity(userId);
        commentMapper.createComment(comment);
    }

    @Override
    public void likeComment(Long commentId, Long userId) {
        // 댓글 좋아요
        commentMapper.likeComment(commentId, userId);
    }

    @Override
    public void cancelLikeComment(Long commentId, Long userId) {
        // 댓글 좋아요 취소
        commentMapper.cancelLikeComment(commentId, userId);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        // 댓글 목록 조회
        List<Comment> comments = commentMapper.getCommentsByPostId(postId);
        return comments.stream()
                .map(CommentDto::fromEntity)
                .collect(Collectors.toList());
    }
}