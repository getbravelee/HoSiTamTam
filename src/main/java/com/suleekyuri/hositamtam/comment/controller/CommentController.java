package com.suleekyuri.hositamtam.comment.controller;

import com.suleekyuri.hositamtam.comment.dto.CommentDto;
import com.suleekyuri.hositamtam.comment.service.CommentService;
import java.util.List;

import com.suleekyuri.hositamtam.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public void createComment(@RequestBody CommentDto commentDto) {
        Long userId = getAuthenticatedUserId(); // 로그인된 사용자 ID 가져오기
        commentService.createComment(commentDto, userId);
    }

    // 댓글 좋아요 (로그인한 회원만 가능)
    @PostMapping("/like/{commentId}")
    public void likeComment(@PathVariable Long commentId) {
        Long userId = getAuthenticatedUserId(); // 로그인된 사용자 ID 가져오기
        commentService.likeComment(commentId, userId);
    }

    // 댓글 좋아요 취소 (로그인한 회원만 가능)
    @PostMapping("/cancel-like/{commentId}")
    public void cancelLikeComment(@PathVariable Long commentId) {
        Long userId = getAuthenticatedUserId(); // 로그인된 사용자 ID 가져오기
        commentService.cancelLikeComment(commentId, userId);
    }

    // 댓글 목록 보기 (로그인 여부와 관계없이 가능)
    @GetMapping("/{postId}")
    public List<CommentDto> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    // 로그인된 사용자 ID를 가져오는 메서드 (인증되지 않은 사용자 처리)
    private Long getAuthenticatedUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == null || !(principal instanceof String)) {
            throw new UnauthorizedException("로그인된 사용자가 아닙니다.");
        }

        // principal을 String에서 Long으로 변환
        try {
            return Long.parseLong((String) principal);
        } catch (NumberFormatException e) {
            throw new UnauthorizedException("유효하지 않은 사용자 ID입니다.");
        }
    }
}