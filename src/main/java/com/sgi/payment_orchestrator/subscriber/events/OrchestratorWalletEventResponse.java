package com.sgi.payment_orchestrator.subscriber.events;

import com.sgi.payment_orchestrator.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrchestratorWalletEventResponse {
    private String status;
    private String transactionId;
    private String walletId;
    private String accountId;
    private String clientId;
    private String type;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private BigDecimal balance;
    private String description;
    private UserDTO sender;
    private UserDTO receiver;

}
