package com.sgi.payment_orchestrator.mapper;

import com.sgi.payment_orchestrator.dto.AccountBalanceRequestDTO;
import com.sgi.payment_orchestrator.dto.OrchestratorRequestDTO;
import com.sgi.payment_orchestrator.dto.OrchestratorResponseDTO;
import com.sgi.payment_orchestrator.dto.TransactionRequestDTO;
import com.sgi.payment_orchestrator.enums.PaymentStatus;
import com.sgi.payment_orchestrator.subscriber.events.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrchestratorMapper {

    OrchestratorMapper INSTANCE = Mappers.getMapper(OrchestratorMapper.class);

    @Mapping(target = "balance", source = "balance")
    OrchestratorRequestDTO map (OrchestratorEvent orchestratorEvent);

    AccountBalanceRequestDTO toAccountBalance (OrchestratorRequestDTO orchestratorRequest);

    @Mapping(target = "productId", source = "accountId")
    TransactionRequestDTO toTransaction (OrchestratorRequestDTO orchestratorRequest);

    OrchestratorEventResponse toOrchestratorResponseEvent(OrchestratorResponseDTO orchestratorResponse);

    OrchestratorResponseDTO toOrchestratorResponse(OrchestratorRequestDTO orchestratorRequest, PaymentStatus status);

    OrchestratorWalletEventResponse toOrchestratorWalletEventResponse(OrchestratorResponseDTO orchestratorResponse);

    OrchestratorBootcoinEventResponse toOrchestratorBootcoinEventResponse(OrchestratorResponseDTO orchestratorResponse);

    CurrentAccountBalanceEventResponse toCurrentBalance(OrchestratorResponseDTO orchestratorResponse);
}
