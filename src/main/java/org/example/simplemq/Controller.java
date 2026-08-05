package org.example.simplemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
@Slf4j
public class Controller {

    @Autowired
    private CbMqservice cbmqservice;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody RequestPayload requestPayload) throws Exception {
        log.info("Entered sendMessage");
        cbmqservice.sendMessage(requestPayload);
        return ResponseEntity.ok().body("Message sent successfully");
    }

}
