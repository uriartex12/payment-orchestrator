package com.sgi.payment_orchestrator.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sgi.payment_orchestrator.enums.TransactionStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionRequestDTO {
    private String id;
    private TransactionStatus status;
    private String productId;
    private BigDecimal amount;
    private String cardId;
    private String clientId;
    private String type;
    private String balance;
}
