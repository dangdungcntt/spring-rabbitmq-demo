package com.nddcoder.demorabbitmq.processor

import com.fasterxml.jackson.databind.ObjectMapper
import com.nddcoder.demorabbitmq.model.Click
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@CompileStatic
@Component
@Slf4j
class ClickProcessor implements MessageListener {

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    MongoTemplate mongoTemplate

    @Override
    void onMessage(String message) {
        log.debug(message)
        Click click = objectMapper.readValue(message, Click)
        mongoTemplate.save(click)
    }
}
