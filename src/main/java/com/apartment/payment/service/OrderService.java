package com.apartment.payment.service;

import com.apartment.payment.dto.CreateOrderRequest;
import com.apartment.payment.dto.OrderResponse;
import com.apartment.payment.entity.MyOrder;
import com.apartment.payment.kafka.dto.PaymentProducerRequest;
import com.apartment.payment.kafka.producer.PaymentProducer;
import com.apartment.payment.repository.OrderRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;
@Component
public class OrderService {

    @Autowired
    PaymentProducer paymentProducer;
    private static final String KEY ="rzp_test_EOEYvNGB8Y4YFd";
    private static final String SECRET ="fPzQzEqdgLVS4bsaEPCPT8cb";
    @Autowired
    OrderRepository orderRepository;
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) throws RazorpayException {
        OrderResponse response = new OrderResponse();
        int parsedAmount = Integer.parseInt(request.getAmount().get());

        RazorpayClient razorpayClient = new RazorpayClient(KEY, SECRET);
        JSONObject ob = new JSONObject();
        ob.put("amount",parsedAmount*100);
        ob.put("currency", "INR");

        Order order = razorpayClient.orders.create(ob);
        System.out.println(order);

        MyOrder myOrder = new MyOrder();
        myOrder.setOrderId(order.get("id"));
        myOrder.setAmount(request.getAmount().get());
        myOrder.setStatus(order.get("status"));
        myOrder.setUserName(request.getUserName().get());
        myOrder.setUserEmail(request.getUserEmail().get());
        myOrder.setFlatNumber(request.getFlatNumber().get());

        try{
            MyOrder mySavedOrder = orderRepository.save(myOrder);
            response.setSuccessMessage("Order saved with order id "+mySavedOrder.getMyOrderId());
        }catch (Exception e) {
            response.setFaliureMessage(e.getMessage());
            return response;
        }
        return response;
    }

    @Transactional
    public OrderResponse updateOrder(CreateOrderRequest request) throws RazorpayException {
        OrderResponse response = new OrderResponse();
        try{
            MyOrder myOrder = orderRepository.findByOrderId(request.getOrderId().get());
            myOrder.setPaymentId(request.getPaymentId().get());
            myOrder.setStatus(request.getStatus().get());
            MyOrder savedOrder = orderRepository.save(myOrder);
            createPaymentProducerRequest(savedOrder);
            response.setSuccessMessage("Successfully update order id "+ myOrder.getMyOrderId());

        } catch (Exception e) {
            response.setFaliureMessage(e.getMessage());
            return response;
        }
        return response;
    }

    private void createPaymentProducerRequest(MyOrder savedOrder) {
        PaymentProducerRequest paymentProducerRequest = new PaymentProducerRequest();
        paymentProducerRequest.setUserName(savedOrder.getUserName());
        paymentProducerRequest.setPaymentStatus(savedOrder.getStatus());
        paymentProducerRequest.setUserEmail(savedOrder.getUserEmail());
        paymentProducerRequest.setFlatNumber(savedOrder.getFlatNumber());
        paymentProducer.sendPaymentNotification(paymentProducerRequest.toString());
    }
}
