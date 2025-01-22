package com.sgi.payment_orchestrator.saga.steps;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import com.sgi.payment_orchestrator.dto.TransactionRequestDTO;
import com.sgi.payment_orchestrator.dto.TransactionResponseDTO;
import com.sgi.payment_orchestrator.enums.TransactionStatus;
import com.sgi.payment_orchestrator.enums.WorkflowStepStatus;
import com.sgi.payment_orchestrator.saga.WorkflowStep;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
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
    public Mono<Pair<Boolean, Object>> process() {
        return this.webClient
                .post()
                .uri("/v1/transactions")
                .body(BodyInserters.fromValue(requestDTO))
                .retrieve()
                .bodyToMono(TransactionResponseDTO.class)
                .doOnNext(t -> this.requestDTO.setId(t.getId()))
                .map(r -> Pair.of(r.getStatus().equals(TransactionStatus.COMPLETED), (Object) r))
                .doOnError(error -> log.error("Error occurred during processing: ", error))
                .doOnNext(pair -> this.stepStatus = pair.getLeft() ? WorkflowStepStatus.COMPLETE : WorkflowStepStatus.FAILED)
                .doOnError(throwable -> requestDTO.setStatus(TransactionStatus.FAILED));
    }

    @Override
    public Mono<Pair<Boolean, Object>> revert() {
        return this.webClient
                .post()
                .uri("/v1/transactions/{transactionId}", this.requestDTO.getId())
                .body(BodyInserters.fromValue(this.requestDTO))
                .retrieve()
                .bodyToMono(TransactionResponseDTO.class)
                .map(r -> Pair.of(true, (Object) r))
                .onErrorResume(throwable -> Mono.just(Pair.of(false, null)))
                .doOnError(throwable -> requestDTO.setStatus(TransactionStatus.FAILED));
    }

}
