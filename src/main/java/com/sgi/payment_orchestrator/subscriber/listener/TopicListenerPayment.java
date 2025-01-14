package com.sgi.payment_orchestrator.subscriber.listener;

import com.sgi.payment_orchestrator.annotations.KafkaController;
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
            topics = OrchestratorEvent.TOPIC
    )
    private void process(OrchestratorEvent orchestratorEvent) {
        orchestratorService.payment(OrchestratorMapper.INSTANCE.map(orchestratorEvent))
                .doOnNext(orchestratorResponse -> {
                    log.warn("Status : " + orchestratorResponse.getStatus());
                    kafkaTemplate.sendEvent(OrchestratorMapper.INSTANCE.toOrchestratorResponse(orchestratorResponse));
                }).subscribe();
    }
}
