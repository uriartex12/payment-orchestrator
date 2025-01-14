package com.sgi.payment_orchestrator.subscriber.events;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY)
public interface PaymentOrchestratorKafkaEvent {}
