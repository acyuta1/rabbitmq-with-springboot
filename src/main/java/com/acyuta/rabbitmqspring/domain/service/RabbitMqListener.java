package com.acyuta.rabbitmqspring.domain.service;

import com.acyuta.rabbitmqspring.domain.model.TestMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

@Service
public class RabbitMqListener {

//    @RabbitListener(queues = "Mobile")
//    public void getMessage(TestMessage testMessage) {
//        System.out.println(testMessage);
//    }

    @RabbitListener(queues = "Mobile")
    public void getMessage(byte[] bytes) throws IOException, ClassNotFoundException {

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInput objectInput = new ObjectInputStream(byteArrayInputStream);
        var testMessage = (TestMessage) objectInput.readObject();
        objectInput.close();
        byteArrayInputStream.close();
        System.out.println(testMessage);
    }
}
