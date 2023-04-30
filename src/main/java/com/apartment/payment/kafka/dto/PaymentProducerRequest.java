package com.apartment.payment.kafka.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentProducerRequest {

    private String userName;

    private String userEmail;

    private String paymentStatus;

    private String flatNumber;

}
