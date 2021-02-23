package com.nddcoder.demorabbitmq.model

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import groovy.transform.CompileStatic
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@CompileStatic
@Document(collection = "clicks")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
class Click {

    @Id
    ObjectId _id

    String target

    String referer

    String userAgent

    String ip

    Date createdAt

}
