package com.shopspree.microservices.order.service;


import com.shopspree.microservices.order.event.OrderPlacedEvent;
import com.shopspree.microservices.order.repository.OrderRepository;
import com.shopspree.microservices.order.client.InventoryClient;
import com.shopspree.microservices.order.dto.OrderRequest;
import com.shopspree.microservices.order.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        boolean isProductInStock = inventoryClient.isInStock(orderRequest.getSkuCode(),orderRequest.getQuantity());

        if(isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setSkuCode(orderRequest.getSkuCode());
            order.setPrice(orderRequest.getPrice());
            order.setQuantity(order.getQuantity());
            orderRepository.save(order);

            //send the message to the Kafka Topic
            //orderNumber, email will be sent for notification service to send out emails
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
            orderPlacedEvent.setOrderNumber(order.getOrderNumber());
            orderPlacedEvent.setEmail(orderRequest.getUserDetails().getEmail());
            orderPlacedEvent.setFirstName(orderRequest.getUserDetails().getFirstName());
            orderPlacedEvent.setLastName(orderRequest.getUserDetails().getLastName());
            log.info("Start - Sending OrderPlacedEvent {} to Kafka topic order-placed", orderPlacedEvent);
            kafkaTemplate.send("order-placed",orderPlacedEvent);
            log.info("End - Sending OrderPlacedEvent {} to Kafka topic order-placed", orderPlacedEvent);
        }else{
            throw new RuntimeException("Product with SkuCode" + orderRequest.getSkuCode() + "is not in stock");
        }




    }
}
