package com.lpdev.financemanagerapi.exceptions;

public class FinanceManagerException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public FinanceManagerException(String message) {
        super(message);
    }
}
