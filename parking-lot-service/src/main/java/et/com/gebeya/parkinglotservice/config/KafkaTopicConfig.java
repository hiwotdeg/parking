package et.com.gebeya.parkinglotservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static et.com.gebeya.parkinglotservice.util.Constant.*;


@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic addLocationTopic() {
        return TopicBuilder.name(ADD_LOCATION)
                .build();
    }


    @Bean
    public NewTopic deleteLocationTopic() {
        return TopicBuilder.name(DELETE_LOCATION)
                .build();
    }

    @Bean
    public NewTopic pushNotificationTopic() {
        return TopicBuilder.name(PUSH_NOTIFICATION)
                .build();
    }

}
