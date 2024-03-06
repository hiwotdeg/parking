package et.com.gebeya.paymentservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import static et.com.gebeya.paymentservice.util.Constant.*;


@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic pushNotificationTopic() {
        return TopicBuilder.name(PUSH_NOTIFICATION)
                .build();
    }

    @Bean
    public NewTopic creditOrDebitMessageTopic() {
        return TopicBuilder.name(CREDIT_OR_DEBIT_MESSAGE)
                .build();
    }

    @Bean
    public NewTopic transferMessageTopic() {
        return TopicBuilder.name(TRANSFER_MESSAGE)
                .build();
    }

}
