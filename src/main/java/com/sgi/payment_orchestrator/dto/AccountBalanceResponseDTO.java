package com.sgi.payment_orchestrator.dto;

import com.sgi.payment_orchestrator.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountBalanceResponseDTO {
    private String id;
    private String type;
    private Object balance;
    private AccountStatus status;
    private String cardId;
    private String accountNumber;
    private String clientId;
}
