package com.apartment.payment.controller;

import com.apartment.payment.dto.CreateOrderRequest;
import com.apartment.payment.dto.OrderResponse;
import com.apartment.payment.service.OrderService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    OrderService orderService;

    @PostMapping("/createOrder")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest orderRequest) throws RazorpayException {

        OrderResponse response = orderService.createOrder(orderRequest);
        if (!StringUtils.isEmpty(response.getFaliureMessage())) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/updateOrder")
    public ResponseEntity<OrderResponse> updateOrder(@RequestBody CreateOrderRequest orderRequest) throws RazorpayException {

        OrderResponse response = orderService.updateOrder(orderRequest);
        if (!StringUtils.isEmpty(response.getFaliureMessage())) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
