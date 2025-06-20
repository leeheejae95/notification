package com.fc.consumer;

import com.fc.event.LikeEvent;
import com.fc.task.LikeAddTask;
import com.fc.task.LikeRemoveTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

import static com.fc.event.LikeEventType.ADD;
import static com.fc.event.LikeEventType.REMOVE;

@Slf4j
@Configuration
public class LikeEventConsumer {

    @Autowired
    private LikeAddTask likeAddTask;

    @Autowired
    private LikeRemoveTask likeRemoveTask;

    @Bean("like")
    public Consumer<LikeEvent> like() {
        return event -> {
            if(event.getType() == ADD) {
                likeAddTask.processEvent(event);
            } else if(event.getType() == REMOVE) {
                likeRemoveTask.processEvent(event);
            }
        };
    }
}
