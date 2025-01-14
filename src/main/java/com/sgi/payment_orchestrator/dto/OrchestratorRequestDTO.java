package com.sgi.payment_orchestrator.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrchestratorRequestDTO {
    private String cardId;
    private String accountId;
    private String clientId;
    private String type;
    private BigDecimal amount;
    private BigDecimal balance;
}
