package com.sgi.payment_orchestrator.subscriber.listener;

import com.sgi.payment_orchestrator.annotations.KafkaController;
import com.sgi.payment_orchestrator.subscriber.events.CurrentAccountBalanceEventResponse;
import com.sgi.payment_orchestrator.subscriber.events.OrchestratorEvent;
import com.sgi.payment_orchestrator.mapper.OrchestratorMapper;
import com.sgi.payment_orchestrator.service.OrchestratorService;
import com.sgi.payment_orchestrator.subscriber.message.EventSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

@KafkaController
@RequiredArgsConstructor
@Slf4j
public class TopicListenerPayment {

    private final OrchestratorService orchestratorService;

    private final EventSender kafkaTemplate;

    @KafkaListener(
            groupId = "${app.name}",
            topics = {OrchestratorEvent.TOPIC_CARD,
                    OrchestratorEvent.TOPIC_WALLET,
                    OrchestratorEvent.TOPIC_BOOTCOIN}
    )
    private void process(OrchestratorEvent orchestratorEvent) {
         orchestratorService.payment(OrchestratorMapper.INSTANCE.map(orchestratorEvent))
                .doOnNext(orchestrator -> {
                    log.info("Payment Status: {}", orchestrator.getStatus());
                    if (orchestratorEvent.getTopic().equals(OrchestratorEvent.TOPIC_WALLET)) {
                        kafkaTemplate.sendEvent(OrchestratorMapper.INSTANCE.toOrchestratorWalletEventResponse(orchestrator));
                    } else if (orchestratorEvent.getTopic().equals(OrchestratorEvent.TOPIC_BOOTCOIN)){
                        kafkaTemplate.sendEvent(OrchestratorMapper.INSTANCE.toOrchestratorBootcoinEventResponse(orchestrator));
                        kafkaTemplate.sendEvent(OrchestratorMapper.INSTANCE.toCurrentBalance(orchestrator));
                    } else {
                        kafkaTemplate.sendEvent(OrchestratorMapper.INSTANCE.toOrchestratorResponseEvent(orchestrator));
                    }
                }).subscribe();
    }
}
