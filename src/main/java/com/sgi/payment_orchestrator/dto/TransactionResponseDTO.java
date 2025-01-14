package com.sgi.payment_orchestrator.dto;

import com.sgi.payment_orchestrator.enums.TransactionStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionResponseDTO {
    private String id;
    private TransactionStatus status;
    private String productId;
    private String type;
    private BigDecimal amount;
    private String clientId;
    private String balance;
    private String createdDate;
}
