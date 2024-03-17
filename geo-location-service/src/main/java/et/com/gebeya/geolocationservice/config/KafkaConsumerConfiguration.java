package et.com.gebeya.geolocationservice.config;

import et.com.gebeya.geolocationservice.dto.AddLocationDto;
import et.com.gebeya.geolocationservice.dto.DeleteLocationDto;
import et.com.gebeya.geolocationservice.dto.UpdateLocationDto;
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

    public KafkaConsumerConfiguration(@Value("${server.kafka.bootstrap-servers}") String bootstrapServer) {
        this.bootstrapServer = bootstrapServer;
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
    public ConsumerFactory<String, AddLocationDto> addLocationConsumerFactory() {
        JsonDeserializer<AddLocationDto> jsonDeserializer = new JsonDeserializer<>(AddLocationDto.class, false);
        jsonDeserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), new ErrorHandlingDeserializer<>(jsonDeserializer));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, AddLocationDto>> addLocationListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AddLocationDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(addLocationConsumerFactory());

        return factory;
    }

    @Bean
    public ConsumerFactory<String, UpdateLocationDto> updateLocationConsumerFactory() {
        JsonDeserializer<UpdateLocationDto> jsonDeserializer = new JsonDeserializer<>(UpdateLocationDto.class, false);
        jsonDeserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), new ErrorHandlingDeserializer<>(jsonDeserializer));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, UpdateLocationDto>> updateLocationListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UpdateLocationDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(updateLocationConsumerFactory());

        return factory;
    }

    @Bean
    public ConsumerFactory<String, DeleteLocationDto> deleteLocationConsumerFactory() {
        JsonDeserializer<DeleteLocationDto> jsonDeserializer = new JsonDeserializer<>(DeleteLocationDto.class, false);
        jsonDeserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), new ErrorHandlingDeserializer<>(jsonDeserializer));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, DeleteLocationDto>> deleteLocationListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DeleteLocationDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(deleteLocationConsumerFactory());

        return factory;
    }
}
