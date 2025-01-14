package com.sgi.payment_orchestrator.saga;

import com.sgi.payment_orchestrator.enums.WorkflowStepStatus;
import reactor.core.publisher.Mono;

public interface WorkflowStep {
    WorkflowStepStatus getStatus();
    Mono<Boolean> process();
    Mono<Boolean> revert();
}
