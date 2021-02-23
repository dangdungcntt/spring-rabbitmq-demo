package com.nddcoder.demorabbitmq.conf

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.nddcoder.demorabbitmq.processor.ClickProcessor
import groovy.transform.CompileStatic
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@CompileStatic
@Configuration
class AppConfig {

    static final String EXCHANGE_NAME = "spring-boot-exchange"

    static final String QUEUE_NAME = "spring-boot"

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper().with {
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
        }
    }

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME)
    }

    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange(EXCHANGE_NAME)
    }

    @Bean
    Binding binding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange)
    }

    @Bean
    SimpleMessageListenerContainer container(
            ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer()
        container.setConnectionFactory(connectionFactory)
        container.setQueueNames(QUEUE_NAME)
        container.setMessageListener(listenerAdapter)
        return container
    }

    @Bean
    MessageListenerAdapter listenerAdapter(ClickProcessor clickProcessor) {
        return new MessageListenerAdapter(clickProcessor, "onMessage")
    }
}
