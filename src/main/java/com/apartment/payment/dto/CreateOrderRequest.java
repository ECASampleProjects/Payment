package com.apartment.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

    private Optional<String> amount;

    private Optional<String> userEmail;

    private Optional<String> userName;

    private Optional<String> paymentId;

    private Optional<String> orderId;

    private Optional<String> status;

    private Optional<String> flatNumber;

}
