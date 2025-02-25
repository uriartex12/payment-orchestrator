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
public class OrchestratorBootcoinEventResponse {
    private String status;
    private String bootcoinId;
    private String accountId;
    private String type;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private BigDecimal balance;
    private String description;
    private UserDTO sender;
    private UserDTO receiver;
    private BigDecimal buyRate;
    private BigDecimal sellRate;
    private String cardId;
    private String operation;
}
