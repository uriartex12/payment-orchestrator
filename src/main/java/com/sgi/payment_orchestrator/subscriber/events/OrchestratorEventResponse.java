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
public class OrchestratorEventResponse {

    private String status;
    private String topic;
    private String walletId;
    private String cardId;
    private String accountId;
    private String bootcoinId;
    private String clientId;
    private String type;
    private BigDecimal amount;
    private BigDecimal buyRate;
    private BigDecimal sellRate;
    private String currency;
    private String paymentMethod;
    private String description;
    private BigDecimal balance;
    private String operation;
    private UserDTO sender;
    private UserDTO receiver;

    public static final String TOPIC = "OrchestratorEventResponse";

}