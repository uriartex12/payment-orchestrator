package com.sgi.payment_orchestrator.dto;

import com.sgi.payment_orchestrator.enums.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrchestratorResponseDTO {
    private PaymentStatus status;
    private String cardId;
    private String accountId;
    private String clientId;
    private String type;
    private BigDecimal amount;
    private BigDecimal balance;
}
