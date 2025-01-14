package com.sgi.payment_orchestrator.saga.steps;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgi.payment_orchestrator.dto.TransactionRequestDTO;
import com.sgi.payment_orchestrator.dto.TransactionResponseDTO;
import com.sgi.payment_orchestrator.enums.TransactionStatus;
import com.sgi.payment_orchestrator.enums.WorkflowStepStatus;
import com.sgi.payment_orchestrator.saga.WorkflowStep;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

public class TransactionStep implements WorkflowStep {

    private final WebClient webClient;
    private final TransactionRequestDTO requestDTO;
    private WorkflowStepStatus stepStatus = WorkflowStepStatus.PENDING;

    public TransactionStep(WebClient webClient, TransactionRequestDTO transactionRequestDTO) {
        this.webClient = webClient;
        this.requestDTO = transactionRequestDTO;
    }

    @Override
    public WorkflowStepStatus getStatus() {
        return this.stepStatus;
    }

    @Override
    public Mono<Boolean> process() {
        return this.webClient
                .post()
                .uri("/v1/transactions")
                .body(BodyInserters.fromValue(requestDTO))
                .retrieve()
                .bodyToMono(TransactionResponseDTO.class)
                .doOnNext(t -> this.requestDTO.setId(t.getId()) )
                .map(r -> r.getStatus().equals(TransactionStatus.COMPLETED))
                .doOnNext(b -> this.stepStatus = b ? WorkflowStepStatus.COMPLETE : WorkflowStepStatus.FAILED)
                .doOnError(throwable -> requestDTO.setStatus(TransactionStatus.FAILED));
    }

    @Override
    public Mono<Boolean> revert() {
        return this.webClient
                .post()
                .uri("/v1/transactions/{transactionId}", this.requestDTO.getId())
                .body(BodyInserters.fromValue(this.requestDTO))
                .retrieve()
                .bodyToMono(Void.class)
                .map(r ->true)
                .onErrorReturn(false);
    }
}
