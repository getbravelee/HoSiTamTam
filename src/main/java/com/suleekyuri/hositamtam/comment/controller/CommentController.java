package com.suleekyuri.hositamtam.comment.controller;

import com.suleekyuri.hositamtam.comment.dto.CommentDto;
import com.suleekyuri.hositamtam.comment.service.CommentService;
import java.util.List;

import com.suleekyuri.hositamtam.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/apt/{aptId}/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 작성 (로그인한 회원만 가능)
    @PostMapping("/create")
    public String createComment(@PathVariable String aptId,
                                @RequestBody CommentDto commentDto) {
        // principal이 String인 경우, Long으로 변환
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId;
        if (principal instanceof String) {
            userId = Long.parseLong((String) principal);  // String을 Long으로 변환
        } else {
            throw new UnauthorizedException("로그인된 사용자가 아닙니다.");
        }
        commentService.createComment(commentDto, userId, aptId);  // 댓글 생성
        return "댓글이 성공적으로 작성되었습니다.";  // 성공 메시지
    }

    // 댓글 좋아요 (로그인한 회원만 가능)
    @PostMapping("/like/{commentId}")
    public String likeComment(@PathVariable String aptId, @PathVariable Long commentId) {
        // principal이 String인 경우, Long으로 변환
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId;
        if (principal instanceof String) {
            userId = Long.parseLong((String) principal);  // String을 Long으로 변환
        } else {
            throw new UnauthorizedException("로그인된 사용자가 아닙니다.");
        }
        commentService.likeComment(commentId, userId);  // 댓글 좋아요 추가
        return "댓글 좋아요가 완료되었습니다.";  // 성공 메시지
    }

    // 댓글 좋아요 취소 (로그인한 회원만 가능)
    @PostMapping("/cancel-like/{commentId}")
    public String cancelLikeComment(@PathVariable String aptId, @PathVariable Long commentId) {
        // principal이 String인 경우, Long으로 변환
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId;
        if (principal instanceof String) {
            userId = Long.parseLong((String) principal);  // String을 Long으로 변환
        } else {
            throw new UnauthorizedException("로그인된 사용자가 아닙니다.");
        }
        commentService.cancelLikeComment(commentId, userId);  // 댓글 좋아요 취소
        return "댓글 좋아요 취소가 완료되었습니다.";  // 성공 메시지
    }

    // 댓글 목록 보기 (로그인 여부와 관계없이 가능)
    @GetMapping()
    public List<CommentDto> getCommentsByAptId(@PathVariable String aptId) {
        return commentService.getCommentsByAptId(aptId);  // 댓글 목록 조회
    }
}