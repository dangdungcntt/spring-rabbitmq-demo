package com.nddcoder.demorabbitmq.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.nddcoder.demorabbitmq.model.Click
import com.nddcoder.demorabbitmq.producer.QueueSender
import groovy.transform.CompileStatic
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@CompileStatic
@RestController
@RequestMapping('/redirect.php')
class ClickController {

    @Autowired
    QueueSender queueSender
    @Autowired
    ObjectMapper objectMapper
    @Value('${rabbitmq.exchange.clicks}')
    String clicksExchangeName

    @GetMapping
    void handle(@RequestParam(name = 'target') String target,
                HttpServletRequest request,
                HttpServletResponse response) {

        Click click = new Click(
                _id: new ObjectId(),
                referer: request.getHeader('referer'),
                userAgent: request.getHeader('user-agent'),
                ip: request.remoteAddr,
                createdAt: new Date()
        )

        click.target = target
                .replaceAll('\\{click_id}', click._id.toString())
                .replaceAll('\\{ip}', click.ip)

        queueSender.send(clicksExchangeName, objectMapper.writeValueAsString(click))

        response.sendRedirect(click.target)
    }
}
