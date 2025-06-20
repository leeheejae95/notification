package com.fc.reopsitory;

import com.fc.IntegrationTest;
import com.fc.domain.CommentNotification;
import com.fc.domain.Notification;
import com.fc.repository.NotificationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.Instant;
import java.util.Optional;

import static com.fc.code.NotificationType.COMMENT;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.*;

class NotificationRepositoryIntegrationTest extends IntegrationTest {

    @Autowired
    private NotificationRepository sut;

    private final long userId = 2L;
    private final long postId = 3L;
    private final long writerId = 4L;
    private final long commentId = 5L;
    private final String comment = "comment";
    private final Instant now = Instant.now();
    private final Instant ninetyDaysAfter = Instant.now().plus(90, DAYS);

    @BeforeEach
    void setUp() {
        for(int i=1; i<=5; i++) {
            Instant occurredAt = now.minus(i, DAYS);
            sut.save(new CommentNotification("id-"+i, userId, COMMENT, occurredAt, now, now, ninetyDaysAfter, postId, writerId, comment, commentId));
        }
    }

    @AfterEach
    void testDown() {
        sut.deleteAll();
    }

    @Test
    void testSave() {
        String id = "1";
        sut.save(createCommentNotification(id));
        Optional<Notification> optionalNotification = sut.findById(id);

        assertTrue(optionalNotification.isPresent());
    }

    @Test
    void testFindById() {
        String id = "2";

        sut.save(createCommentNotification(id));
        Optional<Notification> optionalNotification = sut.findById(id);

        CommentNotification notification = (CommentNotification) optionalNotification.orElseThrow();
        assertEquals(notification.getId(), id);
        assertEquals(notification.getUserId(), userId);
        assertEquals(notification.getOccurredAt().getEpochSecond(), now.getEpochSecond());
        assertEquals(notification.getCreatedAt().getEpochSecond(), now.getEpochSecond());
        assertEquals(notification.getLastUpdateAt().getEpochSecond(), now.getEpochSecond());
        assertEquals(notification.getDeletedAt().getEpochSecond(), ninetyDaysAfter.getEpochSecond());
        assertEquals(notification.getPostId(), postId);
        assertEquals(notification.getWriterId(), writerId);
        assertEquals(notification.getComment(), comment);
        assertEquals(notification.getCommentId(), commentId);
    }

    @Test
    void testDeleteById() {
        String id = "3";

        sut.save(createCommentNotification(id));
        sut.deleteById(id);
        Optional<Notification> optionalNotification = sut.findById(id);

        assertFalse(optionalNotification.isPresent());
    }

    @Test
    void test_findAllByUserIdOrderByOccurredAtDesc() {
        Pageable page = PageRequest.of(0,3);
        Slice<Notification> result = sut.findAllByUserIdOrderByOccurredAtDesc(userId, page);

        assertEquals(3,result.getContent().size());
        assertTrue(result.hasNext()); // 5개 중 3개만 들어오고 2개가 남음

        Notification first = result.getContent().get(0);
        Notification second = result.getContent().get(1);
        Notification third = result.getContent().get(2);

        assertTrue(first.getOccurredAt().isAfter(second.getOccurredAt()));
        assertTrue(second.getOccurredAt().isAfter(third.getOccurredAt()));
    }

    @Test
    void test_findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc_firstQuery() {
        Pageable page = PageRequest.of(0,3);
        Slice<Notification> result = sut.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId,now,page);

        assertEquals(3,result.getContent().size());
        assertTrue(result.hasNext());

        Notification first = result.getContent().get(0);
        Notification second = result.getContent().get(1);
        Notification third = result.getContent().get(2);

        assertTrue(first.getOccurredAt().isAfter(second.getOccurredAt()));
        assertTrue(second.getOccurredAt().isAfter(third.getOccurredAt()));

    }

    @Test
    void test_findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc_secondQueryWithPivot() {
        Pageable page = PageRequest.of(0,3);
        Slice<Notification> firstResult = sut.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId,now,page); // now보다 이전의 시간 조회

        Notification last = firstResult.getContent().get(2); // 조회된 데이터 3개중 가장 오래된 알림

        Instant pivot = last.getOccurredAt();
        Slice<Notification> secondResult = sut.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId,pivot,page); // pivot보다 이전의 시간 조회

        assertEquals(2,secondResult.getContent().size()); // 조회 결과 사이즈가 같은지 테스트
        assertFalse(secondResult.hasNext()); // 더 이상 가져올거 없음
    }

    private CommentNotification createCommentNotification(String id) {
        return new CommentNotification(id, userId, COMMENT, now, now, now, ninetyDaysAfter, postId, writerId, comment,
                commentId);
    }
}