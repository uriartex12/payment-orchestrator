package com.sgi.payment_orchestrator.saga;

import java.util.List;

public interface Workflow {
    List<WorkflowStep> getSteps();
}
