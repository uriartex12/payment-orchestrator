package com.sgi.payment_orchestrator.controller;

import com.sgi.payment_orchestrator.subscriber.events.OrchestratorEvent;
import com.sgi.payment_orchestrator.subscriber.message.EventSender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final EventSender kafkaTemplate;

    @PostMapping("/send")
    public void sendMessage() {
        OrchestratorEvent orchestratorEvent = OrchestratorEvent.builder()
                .accountId("6781f07fd3edff314170b477")
                .type("DEPOSIT")
                .clientId("c19dc55e-4e75-4ae4-b71c-b11ea95498bf")
                .amount(BigDecimal.ONE)
                .balance(BigDecimal.TEN)
                .cardId(UUID.randomUUID().toString())
                .build();
        kafkaTemplate.sendEvent(orchestratorEvent);
    }
}
