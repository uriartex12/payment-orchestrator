package com.sgi.payment_orchestrator.dto;

import com.sgi.payment_orchestrator.enums.AccountStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountBalanceResponseDTO {
    private String id;
    private String type;
    private Object balance;
    private AccountStatus status;
    private String cardId;
    private String accountNumber;
    private String clientId;
}
