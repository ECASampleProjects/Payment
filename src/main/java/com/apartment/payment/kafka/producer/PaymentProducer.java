package com.apartment.payment.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentProducer {
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Value("${apache.kafka.topic}")
    private String topic;

    public void sendPaymentNotification(String message) {
        kafkaTemplate.send(topic,message);
    }

}
