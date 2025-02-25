package com.sgi.payment_orchestrator.subscriber.events;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class OrchestratorEvent {

    public static final String TOPIC_CARD = "OrchestratorEvent";
    public static final String TOPIC_WALLET = "OrchestratorWalletEvent";
    public static final String TOPIC_BOOTCOIN = "OrchestratorBootcoinEvent";

    @JsonProperty("@topic")
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
}
