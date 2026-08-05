package org.example.simplemq;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CbMqservice {

    @Autowired
    private Mqservice mqservice;

    @Autowired
    private FailedMessageRepo failedMessageRepo;
    @Autowired
    private JmsTemplate jmsTemplate;

    @CircuitBreaker(name = "mycircuitbreaker", fallbackMethod = "fallback")
    public void sendMessage(RequestPayload requestPayload) {
        log.info("Entered circuit breaker");
        mqservice.sendMessage(requestPayload);
        log.info("Message sent successfully from circuit breaker");
    }

    public void fallback(RequestPayload requestPayload, Throwable throwable) {
        log.error("Fallback Exception {}", throwable + requestPayload.toString());
        MQEntity msg = new MQEntity();
        msg.setPayload(requestPayload.toString());
        msg.setError_message(throwable.getMessage());
        failedMessageRepo.save(msg);
    }

    @Scheduled(fixedRate = 60000)
    public void processFailedMessages() {
        log.info("Entered Reprocessing Failed Messages");
        List<MQEntity> failedMessages = failedMessageRepo.findByProcessedFalse();
            for (MQEntity msg : failedMessages) {
                try {
                    jmsTemplate.convertAndSend("test-queue", msg.getPayload());
                    log.info("Message reprocessed successfully "+msg.getPayload());
                    msg.setProcessed(true);
                    failedMessageRepo.save(msg);
                } catch (JmsException e) {
                    throw new RuntimeException(e);
                }
            }

        }
}
