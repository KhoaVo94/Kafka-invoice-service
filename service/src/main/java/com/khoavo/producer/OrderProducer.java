package com.khoavo.producer;

import com.khoavo.domain.generated.PaidOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class OrderProducer {

    @Value("${spring.kafka.template.default-topic}")
    String topic;
    final KafkaTemplate<String, PaidOrder> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, PaidOrder> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(PaidOrder paidOrder) {
        ProducerRecord<String, PaidOrder> producerRecord = new ProducerRecord<>(topic, paidOrder.getId(), paidOrder);
        ListenableFuture<SendResult<String, PaidOrder>> listenableFuture = kafkaTemplate.send(producerRecord);
        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                handleFailure(paidOrder, throwable);
            }

            @Override
            public void onSuccess(SendResult<String, PaidOrder> result) {
                handleSuccess(paidOrder, result);
            }
        });
    }

    private void handleFailure(PaidOrder paidOrder, Throwable throwable) {
        log.error("Error sending the message for {} and the exception is {}", paidOrder, throwable.getMessage());
    }

    private void handleSuccess(PaidOrder paidOrder, SendResult<String, PaidOrder> result) {
        log.info("Message sent successFully for the key : {} and the value is {} , partition is {}",
                paidOrder.getId(), paidOrder, result.getRecordMetadata().partition());
    }

}
