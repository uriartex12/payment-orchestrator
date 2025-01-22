package com.sgi.payment_orchestrator.subscriber.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentAccountBalanceEventResponse {

    private String accountId;
    private String type;
    private Object amount;

    public static final String TOPIC = "CurrentBalanceEventResponse";
}