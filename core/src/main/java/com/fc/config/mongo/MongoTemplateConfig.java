package com.fc.config.mongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = "com.fc", // 경로 밑에 있는 파일 스캔하기
        mongoTemplateRef = MongoTemplateConfig.MONGO_TEMPLATE
)
public class MongoTemplateConfig {

    public static final String MONGO_TEMPLATE = "notificationMongoTemplate";

    @Bean(name = MONGO_TEMPLATE)
    public MongoTemplate notificationMongoTemplate(
            MongoDatabaseFactory notificationMongoFactory,
            MongoConverter mongoConverter
    ) {
        return new MongoTemplate(notificationMongoFactory, mongoConverter);
    }
}
