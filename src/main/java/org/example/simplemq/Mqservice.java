package org.example.simplemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@Slf4j
public class Mqservice {

    @Autowired
    private JmsTemplate jmsTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    @Retryable(maxAttempts = 3,backoff = @Backoff(delay = 5000))
    public void sendMessage(RequestPayload message){
        log.info("Entered sendMessage service");
        String json = mapper.writeValueAsString(message);
        log.info("Attempting to send message to MQ: {}", json);
        jmsTemplate.convertAndSend("test-queue", json);
        log.info("Message sent successfully" + json);
    }

}
