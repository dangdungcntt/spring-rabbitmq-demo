package com.nddcoder.demorabbitmq.producer

import groovy.transform.CompileStatic
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@CompileStatic
@Component
class QueueSender {

    @Autowired
    RabbitTemplate rabbitTemplate

    void send(String exchange, String message) {
        rabbitTemplate.convertAndSend(exchange, '', message)
    }
}
