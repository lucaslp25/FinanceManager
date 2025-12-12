package com.lpdev.financemanagerapi.exceptions;

public class FinanceManagerNotFoundException extends FinanceManagerException {
    private static final long serialVersionUID = 1L;
    public FinanceManagerNotFoundException(String message) {
        super(message);
    }
}
