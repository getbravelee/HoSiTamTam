package com.suleekyuri.hositamtam.comment.dto;

import com.suleekyuri.hositamtam.comment.Comment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;
    private Long userId;
    private String nickname;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int likes;
    private String imageUrls;
    private String aptId;

    // 엔티티로 변환
    public Comment toEntity(Long userId, String aptId) {
        return Comment.builder()
                .userId(userId)
                .nickname(this.nickname)
                .content(this.content)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .likes(0)  // 기본값
                .imageUrls(this.imageUrls)
                .aptId(aptId)  // aptId를 전달하여 설정
                .build();
    }

    // 엔티티를 DTO로 변환
    public static CommentDto fromEntity(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .userId(comment.getUserId())
                .nickname(comment.getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .likes(comment.getLikes())
                .imageUrls(comment.getImageUrls())
                .aptId(comment.getAptId())
                .build();
    }
}
