package com.sgi.payment_orchestrator.saga;

import java.util.List;

public class PaymentWorkflow implements Workflow {

    private final List<WorkflowStep> steps;

    public PaymentWorkflow(List<WorkflowStep> steps) {
        this.steps = steps;
    }

    @Override
    public List<WorkflowStep> getSteps() {
        return steps;
    }
}
