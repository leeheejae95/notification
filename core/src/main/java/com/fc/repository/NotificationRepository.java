package com.fc.repository;

import com.fc.domain.Notification;
import com.fc.code.NotificationType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    Optional<Notification> findById(Long id);

    Notification save(Notification notification);

    void deleteById(String id);

    @Query("{ 'type': ?0, 'commentId': ?1 }")
    Optional<Notification> findByTypeAndCommentId(NotificationType notificationType, Long commentId);

    @Query("{ 'type': ?0, 'postId': ?1 }")
    Optional<Notification> findByTypeAndPostId(NotificationType notificationType, Long postId);

    @Query("{ 'type': ?0, 'userId': ?1, 'followerId': ?2 }")
    Optional<Notification> findByTypeAndUserIdAndFollowerId(NotificationType type, Long userId, Long followerId);

    Slice<Notification> findAllByUserIdOrderByOccurredAtDesc(Long userId, Pageable page);

    Slice<Notification> findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(Long userId, Instant occurredAt, Pageable page);
}
