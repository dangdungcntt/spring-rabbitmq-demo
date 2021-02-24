package com.nddcoder.demorabbitmq.processor

import com.fasterxml.jackson.databind.ObjectMapper
import com.nddcoder.demorabbitmq.model.Click
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.amqp.core.ExchangeTypes
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@CompileStatic
@Component
@Slf4j
class ClickProcessor {

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    MongoTemplate mongoTemplate

    @RabbitListener(
            concurrency = '2-10',
            bindings = @QueueBinding(
                    value = @Queue('${rabbitmq.queues.clicks}'),
                    exchange = @Exchange(name = '${rabbitmq.exchange.clicks}', type = ExchangeTypes.DIRECT)
            )
    )
    void onMessage(String message) {
        Click click = objectMapper.readValue(message, Click)
        mongoTemplate.save(click)
    }
}
