package com.sgi.payment_orchestrator.saga;

import com.sgi.payment_orchestrator.enums.WorkflowStepStatus;
import org.apache.commons.lang3.tuple.Pair;
import reactor.core.publisher.Mono;

public interface WorkflowStep {
    WorkflowStepStatus getStatus();
    Mono<Pair<Boolean, Object>> process();
    Mono<Pair<Boolean, Object>> revert();
}
