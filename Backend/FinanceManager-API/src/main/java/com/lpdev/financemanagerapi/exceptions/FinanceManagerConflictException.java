package com.lpdev.financemanagerapi.exceptions;

public class FinanceManagerConflictException extends FinanceManagerException{
    private static final long serialVersionUID = 1L;
    public FinanceManagerConflictException(String message) {
        super(message);
    }
}
