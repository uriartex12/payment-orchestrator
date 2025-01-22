package com.sgi.payment_orchestrator.service;

import com.sgi.payment_orchestrator.dto.AccountBalanceResponseDTO;
import com.sgi.payment_orchestrator.dto.OrchestratorRequestDTO;
import com.sgi.payment_orchestrator.dto.OrchestratorResponseDTO;
import com.sgi.payment_orchestrator.dto.TransactionResponseDTO;
import com.sgi.payment_orchestrator.enums.PaymentStatus;
import com.sgi.payment_orchestrator.enums.WorkflowStepStatus;
import com.sgi.payment_orchestrator.exception.WorkflowException;
import com.sgi.payment_orchestrator.mapper.OrchestratorMapper;
import com.sgi.payment_orchestrator.saga.WorkflowStep;
import com.sgi.payment_orchestrator.saga.steps.AccountStep;
import com.sgi.payment_orchestrator.saga.PaymentWorkflow;
import com.sgi.payment_orchestrator.saga.steps.TransactionStep;
import com.sgi.payment_orchestrator.saga.Workflow;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrchestratorService {

    @Autowired
    @Qualifier("account")
    private WebClient accountClient;

    @Autowired
    @Qualifier("transaction")
    private WebClient transactionClient;

    @SneakyThrows
    public Mono<OrchestratorResponseDTO> payment(final OrchestratorRequestDTO requestDTO) {
        Workflow orderWorkflow = this.getOrderWorkflow(requestDTO);
         return Flux.fromStream(() -> orderWorkflow.getSteps().stream())
                .flatMap(WorkflowStep::process)
                .handle(((result, synchronousSink) -> {
                    if (result.getLeft()){
                        if(result.getRight() instanceof TransactionResponseDTO transactionResponse){
                            requestDTO.setTransactionId(transactionResponse.getId());
                        }
                        synchronousSink.next(true);
                    }
                    else
                        synchronousSink.error(new WorkflowException("create order failed!"));
                }))
                .then(Mono.fromCallable(() -> getResponseDTO(requestDTO, PaymentStatus.PAYMENT_COMPLETED)))
                .onErrorResume(ex -> this.revertPayment(orderWorkflow, requestDTO));
    }

    private Mono<OrchestratorResponseDTO> revertPayment(final Workflow workflow, final OrchestratorRequestDTO requestDTO){
        return Flux.fromStream(() -> workflow.getSteps().stream())
                .filter(wf -> wf.getStatus().equals(WorkflowStepStatus.COMPLETE))
                .flatMap(WorkflowStep::revert)
                .retry(3)
                .then(Mono.just(this.getResponseDTO(requestDTO, PaymentStatus.PAYMENT_CANCELLED)));
    }

    private Workflow getOrderWorkflow(OrchestratorRequestDTO requestDTO) {
        WorkflowStep accountStep = new AccountStep(this.accountClient, OrchestratorMapper.INSTANCE.toAccountBalance(requestDTO));
        WorkflowStep transactionStep = new TransactionStep(this.transactionClient, OrchestratorMapper.INSTANCE.toTransaction(requestDTO));
        return new PaymentWorkflow(List.of(accountStep, transactionStep));
    }

    private OrchestratorResponseDTO getResponseDTO(OrchestratorRequestDTO requestDTO, PaymentStatus status) {
        return  OrchestratorMapper.INSTANCE.toOrchestratorResponse(requestDTO, status);
    }
}
