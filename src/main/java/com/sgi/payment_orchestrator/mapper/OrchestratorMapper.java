package com.sgi.payment_orchestrator.mapper;

import com.sgi.payment_orchestrator.dto.AccountBalanceRequestDTO;
import com.sgi.payment_orchestrator.dto.OrchestratorRequestDTO;
import com.sgi.payment_orchestrator.dto.OrchestratorResponseDTO;
import com.sgi.payment_orchestrator.dto.TransactionRequestDTO;
import com.sgi.payment_orchestrator.subscriber.events.OrchestratorEvent;
import com.sgi.payment_orchestrator.subscriber.events.OrchestratorEventResponse;
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

    OrchestratorEventResponse toOrchestratorResponse(OrchestratorResponseDTO orchestratorResponseDTO);

}
