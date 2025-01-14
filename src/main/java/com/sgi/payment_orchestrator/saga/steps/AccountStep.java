package com.sgi.payment_orchestrator.saga.steps;

import com.sgi.payment_orchestrator.dto.AccountBalanceRequestDTO;
import com.sgi.payment_orchestrator.dto.AccountBalanceResponseDTO;
import com.sgi.payment_orchestrator.enums.AccountStatus;
import com.sgi.payment_orchestrator.enums.WorkflowStepStatus;
import com.sgi.payment_orchestrator.saga.WorkflowStep;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
public class AccountStep implements WorkflowStep {

    private final WebClient webClient;
    private final AccountBalanceRequestDTO requestDTO;
    private WorkflowStepStatus stepStatus = WorkflowStepStatus.PENDING;

    public AccountStep(WebClient webClient, AccountBalanceRequestDTO requestDTO) {
        this.webClient = webClient;
        this.requestDTO = requestDTO;
    }

    @Override
    public WorkflowStepStatus getStatus() {
        return this.stepStatus;
    }

    @Override
    public Mono<Boolean> process() {
        return this.webClient
                .post()
                .uri("/v1/accounts/balance/deduct")
                .body(BodyInserters.fromValue(this.requestDTO))
                .retrieve()
                .bodyToMono(AccountBalanceResponseDTO.class)
                .doOnSuccess(response -> {
                   log.info("Response Balance {} - payload {}: ", response.getStatus(), response);
                })
                .map(r -> r.getStatus().equals(AccountStatus.COMPLETED))
                .doOnNext(b -> this.stepStatus = b ? WorkflowStepStatus.COMPLETE : WorkflowStepStatus.FAILED);
    }

    @Override
    public Mono<Boolean> revert() {
        return this.webClient
                .post()
                .uri("/v1/accounts/balance/add")
                .body(BodyInserters.fromValue(this.requestDTO))
                .retrieve()
                .bodyToMono(Void.class)
                .map(r ->true)
                .onErrorReturn(false);
    }
}
