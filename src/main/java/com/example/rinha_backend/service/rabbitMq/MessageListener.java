package com.example.rinha_backend.service.rabbitMq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @RabbitListener(queues = "myQueue") // Nome da fila
    public void receiveMessage(String message) {
        System.out.println("Received: " + message);
    }
}