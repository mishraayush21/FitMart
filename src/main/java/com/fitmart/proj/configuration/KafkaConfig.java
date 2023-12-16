package com.fitmart.proj.configuration;

import com.fitmart.proj.global.GlobalData;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic newTopic() {
        return TopicBuilder
                .name(GlobalData.TOPIC_NAME)
//                .partitions()
//                .replicas()
                .build();
    }
}
