package com.sgi.payment_orchestrator.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sgi.payment_orchestrator.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {
    private String id;
    private TransactionStatus status;
    private String walletId;
    private String productId;
    private BigDecimal amount;
    private String bootcoinId;
    private BigDecimal buyRate;
    private BigDecimal sellRate;
    private String currency;
    private String paymentMethod;
    private String cardId;
    private String clientId;
    private String type;
    private String balance;
    private String operation;
    private String description;
    private UserDTO sender;
    private UserDTO receiver;
}
