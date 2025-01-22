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
    private String walletId;
    private String source;
    private String description;
    private String topic;
    private String bootcoinId;
    private BigDecimal buyRate;
    private BigDecimal sellRate;
    private String currency;
    private String paymentMethod;
    private String transactionId;
    private String operation;
    private UserDTO sender;
    private UserDTO receiver;
}
