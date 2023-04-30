package com.apartment.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myOrderId;

    @Column(name="order_id")
    private String orderId;

    @Column(name="amount")
    private String amount;

    @Column(name="status")
    private String status;

    @Column(name="user_name")
    private String userName;

    @Column(name="user_email")
    private String userEmail;

    @Column(name="payment_id")
    private String paymentId;

    @Column(name="flat_number")
    private String flatNumber;

}
