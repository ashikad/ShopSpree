package com.shopspree.microservices.service;

import com.shopspree.microservices.order.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "order-placed")
    public void listen(OrderPlacedEvent orderPlacedEvent){
        log.info("Got message from the order-placed topic {}",orderPlacedEvent);

        //send email to the customer
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("shopspree@gmail.com");
            mimeMessageHelper.setTo(orderPlacedEvent.getEmail().toString());
            mimeMessageHelper.setSubject(String.format("Your Order with OrderNumber %s is placed successfully", orderPlacedEvent.getOrderNumber()));
            mimeMessageHelper.setText(String.format(""" 
                            Hi %s, %s

                            Your order with order number %s is now placed successfully.
                            
                            Best Regards
                            Spring Shop
                            """,
                    orderPlacedEvent.getFirstName().toString(),
                    orderPlacedEvent.getLastName().toString(),
                    orderPlacedEvent.getOrderNumber()));
        };
        try{
            javaMailSender.send(mimeMessagePreparator);
            log.info("Order Notification email sent!");
        }catch (MailException e){
            log.error("Exception occurred when sending mail",e);
            throw new RuntimeException("Exception occurred when sending mail from shopspree",e);
        }


    }
}
