package com.suleekyuri.hositamtam.comment;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private Long id;  // 댓글 ID
    private Long postId;  // 게시글 ID
    private Long userId;  // 작성자 ID
    private String nickname;  // 작성자 닉네임
    private String content;  // 댓글 내용
    private LocalDateTime createdAt;  // 작성일
    private LocalDateTime updatedAt;  // 수정일
    private int likes;  // 좋아요 수
    private String imageUrls;  // 이미지 URL들 (최대 3개)

    // 좋아요 수를 증가시키는 메서드
    public void incrementLikes() {
        this.likes++;
    }

    // 좋아요 수를 감소시키는 메서드
    public void decrementLikes() {
        if (this.likes > 0) {
            this.likes--;
        }
    }

    // 이미지 URL을 추가하는 메서드
    public void addImageUrl(String imageUrl) {
        if (this.imageUrls == null || this.imageUrls.isEmpty()) {
            this.imageUrls = imageUrl;
        } else {
            this.imageUrls += "," + imageUrl;
        }
    }

    // 댓글을 수정하는 메서드
    public void updateContent(String newContent) {
        this.content = newContent;
        this.updatedAt = LocalDateTime.now();  // 수정된 시간 업데이트
    }
}
