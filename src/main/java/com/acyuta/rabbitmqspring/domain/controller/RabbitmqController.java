package com.acyuta.rabbitmqspring.domain.controller;

import com.acyuta.rabbitmqspring.domain.model.TestMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RabbitmqController {


    private final RabbitTemplate rabbitTemplate;

    @GetMapping("/send-rabbit-mq")
    public String sendToRabbitQueue(){

        // Class which is being instantiated must be serializable.
        var testMessage = new TestMessage().setBy("acyuta").setContent("From spring boot").setId(1L);

        // Send directly to queue.
        rabbitTemplate.convertAndSend("Mobile", testMessage);

        // Send through different exchange types (direct, fanout, topic) except for header-exchange.

        rabbitTemplate.convertAndSend("Direct-Exchange","mobile", testMessage);
        rabbitTemplate.convertAndSend("Fanout-Exchange", "", testMessage);
        rabbitTemplate.convertAndSend("Topic-Exchange", "tv.mobile.ac", testMessage);

        return "success";
    }

    @GetMapping("/send-rabbit-mq/headers")
    public String sendToRabbitQueueHeadersExchange() throws IOException {

        // Class which is being instantiated must be serializable.
        var testMessage = new TestMessage().setBy("acyuta").setContent("From spring boot").setId(1L);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutput objectOutput = new ObjectOutputStream(byteArrayOutputStream);
        objectOutput.writeObject(testMessage);
        objectOutput.flush();
        objectOutput.close();

        byte[] byteArray = byteArrayOutputStream.toByteArray();
        Message message = MessageBuilder.withBody(byteArray).setHeader("item1","mobile").build();
        rabbitTemplate.send("Header-exchange","",message);

        return "success";
    }
}
