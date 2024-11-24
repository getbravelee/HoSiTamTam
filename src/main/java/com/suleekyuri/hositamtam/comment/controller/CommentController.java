package com.suleekyuri.hositamtam.comment.controller;

import com.suleekyuri.hositamtam.comment.dto.CommentDto;
import com.suleekyuri.hositamtam.comment.service.CommentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 작성 (로그인한 회원만 가능)
    @PostMapping("/create")
    public void createComment(@RequestBody CommentDto commentDto, @RequestHeader("userId") Long userId) {
        // 댓글 작성은 로그인된 사용자의 userId만 가능
        commentService.createComment(commentDto, userId);
    }

    // 댓글 좋아요 (로그인한 회원만 가능)
    @PostMapping("/like/{commentId}")
    public void likeComment(@PathVariable Long commentId, @RequestHeader("userId") Long userId) {
        // 댓글 좋아요는 로그인된 사용자의 userId만 가능
        commentService.likeComment(commentId, userId);
    }

    // 댓글 좋아요 취소 (로그인한 회원만 가능)
    @PostMapping("/cancel-like/{commentId}")
    public void cancelLikeComment(@PathVariable Long commentId, @RequestHeader("userId") Long userId) {
        // 댓글 좋아요 취소는 로그인된 사용자의 userId만 가능
        commentService.cancelLikeComment(commentId, userId);
    }

    // 댓글 목록 보기 (로그인 여부와 관계없이 가능)
    @GetMapping("/{postId}")
    public List<CommentDto> getCommentsByPostId(@PathVariable Long postId) {
        // 댓글 목록은 로그인하지 않은 사용자도 볼 수 있도록 구현
        return commentService.getCommentsByPostId(postId);
    }
}