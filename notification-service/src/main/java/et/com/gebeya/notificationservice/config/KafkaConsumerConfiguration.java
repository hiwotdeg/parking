package et.com.gebeya.notificationservice.config;

import et.com.gebeya.notificationservice.dto.MessageDto;
import et.com.gebeya.notificationservice.dto.Otpdto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfiguration {
    private final String bootstrapServer;
    public KafkaConsumerConfiguration( @Value("${spring.kafka.bootstrap-servers}") String kafkaUrl)
    {
        this.bootstrapServer=kafkaUrl;
    }

    public Map<String, Object> consumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<String, String> stringConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> factory(ConsumerFactory<String, String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Otpdto> messageConsumerFactory() {
        JsonDeserializer<Otpdto> jsonDeserializer = new JsonDeserializer<>(Otpdto.class, false);
        jsonDeserializer.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), new ErrorHandlingDeserializer<>(jsonDeserializer));
    }


    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Otpdto>> messagedtoListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Otpdto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(messageConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, MessageDto> pushNotificationConsumerFactory() {
        JsonDeserializer<MessageDto> jsonDeserializer = new JsonDeserializer<>(MessageDto.class, false);
        jsonDeserializer.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), new ErrorHandlingDeserializer<>(jsonDeserializer));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, MessageDto>> pushNotificationListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MessageDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(pushNotificationConsumerFactory());
        return factory;
    }

}
