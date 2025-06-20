package com.fc.task;

import com.fc.client.CommentClient;
import com.fc.client.PostClient;
import com.fc.code.NotificationType;
import com.fc.domain.Comment;
import com.fc.domain.CommentNotification;
import com.fc.domain.Notification;
import com.fc.domain.Post;
import com.fc.event.CommentEvent;
import com.fc.service.NotificationSaveService;
import com.fc.util.NotificationIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Component
public class CommentAddTask {

    @Autowired
    PostClient postClient;

    @Autowired
    CommentClient commentClient;

    @Autowired
    NotificationSaveService notificationSaveService;

    // 비즈니스 로직 구현
    public void processEvent(CommentEvent event) {
        // 내가 작성한 댓글인 경우 무시 ( REST API 호출 : 개시글 서버 있다라고 가정 )
        Post post = postClient.getPost(event.getPostId());
        if(Objects.equals(post.getUserId(), event.getUserId())) {
            return;
        }

        Comment comment = commentClient.getComment(event.getCommentId()); // 댓글 가져오기

        // 댓글 정보로 알림생성
        Notification notification = createNotification(post, comment);

        // 저장
        notificationSaveService.insert(notification);
    }

    private Notification createNotification(Post post, Comment comment) {
        Instant now = Instant.now();
        return new CommentNotification(
                NotificationIdGenerator.generate(),
                post.getUserId(), // 포스트의 유저
                NotificationType.COMMENT,
                comment.getCreatedAt(), // 댓글 이벤트 발생 시간
                now, // 생성시간
                now, // 마지막 수정시간
                now.plus(90, ChronoUnit.DAYS), // 언제 알림 삭제할건지
                post.getId(), // 게시글
                comment.getUserId(), // 댓글단 사용자
                comment.getContent(), // 댓글 내용
                comment.getId() // 댓글 아이디
        );
    }
}
