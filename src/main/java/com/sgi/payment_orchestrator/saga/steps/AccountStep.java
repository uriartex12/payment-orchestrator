package com.sgi.payment_orchestrator.saga.steps;

import com.sgi.payment_orchestrator.dto.AccountBalanceRequestDTO;
import com.sgi.payment_orchestrator.dto.AccountBalanceResponseDTO;
import com.sgi.payment_orchestrator.dto.TransactionResponseDTO;
import com.sgi.payment_orchestrator.enums.AccountStatus;
import com.sgi.payment_orchestrator.enums.WorkflowStepStatus;
import com.sgi.payment_orchestrator.saga.WorkflowStep;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
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
    public Mono<Pair<Boolean, Object>> process() {
        return this.webClient
                .post()
                .uri("/v1/accounts/balance/".concat(getAction(this.requestDTO.getType(), "PROCESS")))
                .body(BodyInserters.fromValue(this.requestDTO))
                .retrieve()
                .bodyToMono(AccountBalanceResponseDTO.class)
                .doOnSuccess(response -> {
                    log.info("Response Balance {} - payload {}: ", response.getStatus(), response);
                })
                .doOnError(error -> log.error("Error occurred during processing: ", error))
                .map(r -> Pair.of(r.getStatus().equals(AccountStatus.COMPLETED), (Object) r))
                .doOnNext(pair -> this.stepStatus = pair.getLeft() ? WorkflowStepStatus.COMPLETE : WorkflowStepStatus.FAILED);
    }



    @Override
    public Mono<Pair<Boolean, Object>> revert() {
        return this.webClient
                .post()
                .uri("/v1/accounts/balance/".concat(getAction(this.requestDTO.getType(), "REVERT")))
                .body(BodyInserters.fromValue(this.requestDTO))
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(error -> log.error("Error occurred during processing: ", error))
                .map(r -> Pair.of(true, (Object) r))
                .onErrorReturn(Pair.of(false, null));
    }


    private String getAction(String type, String step) {
        return switch (step) {
            case "PROCESS" -> switch (type) {
                case "DEBIT", "PAYMENT", "WITHDRAWAL", "PURCHASE" -> "deduct";
                case "CREDIT", "DEPOSIT", "SALE" -> "add";
                default -> "";
            };
            case "REVERT" -> switch (type) {
                case "DEBIT", "PAYMENT", "WITHDRAWAL", "PURCHASE" -> "add";
                case "CREDIT", "DEPOSIT", "SALE" -> "deduct";
                default -> "";
            };
            default -> "";
        };
    }
}
