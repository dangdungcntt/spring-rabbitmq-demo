package com.nddcoder.demorabbitmq.processor

interface MessageListener {
    void onMessage(String message)
}