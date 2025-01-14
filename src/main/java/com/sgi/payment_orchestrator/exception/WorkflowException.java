package com.sgi.payment_orchestrator.exception;

public class WorkflowException extends RuntimeException {
    public WorkflowException(String message) {
        super(message);
    }
}
