package com.example.rinha_backend.controller;
import com.example.rinha_backend.service.rabbitMq.MessageSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private MessageSender messageSender;

    @PostMapping("/send")
    public void sendMessage(@RequestBody String message) {
        messageSender.sendMessage("myQueue", message);
    }
}