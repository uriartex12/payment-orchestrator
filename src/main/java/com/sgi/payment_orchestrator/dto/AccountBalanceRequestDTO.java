package com.sgi.payment_orchestrator.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountBalanceRequestDTO {
    private String accountId;
    private BigDecimal amount;
    private String type;
}
