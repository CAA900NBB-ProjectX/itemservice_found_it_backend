package com.projectx.foundit.service;

import com.projectx.foundit.config.RabbitMQConfig;
import com.projectx.foundit.model.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRequestService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public User requestUserDetails(String userId) {
        // Send user ID as a message and wait for response
        return (User) rabbitTemplate.convertSendAndReceive(
                RabbitMQConfig.REQUEST_QUEUE, userId);
    }
}
