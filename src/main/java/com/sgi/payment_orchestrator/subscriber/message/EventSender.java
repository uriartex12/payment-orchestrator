package com.sgi.payment_orchestrator.subscriber.message;

import com.sgi.payment_orchestrator.mapper.ObjectMappers;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class EventSender {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public EventSender(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Object event) {
        try {
            String topic = event.getClass().getSimpleName();
            String value = ObjectMappers.OBJECT_MAPPER.writeValueAsString(event);
            ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(topic, value);
            kafkaTemplate.send(producerRecord);
            log.info("Publishing to Kafka topic {}: {}", topic, event);
        } catch (Exception e) {
            log.error("Error publishing to Kafka", e);
        }
    }
}