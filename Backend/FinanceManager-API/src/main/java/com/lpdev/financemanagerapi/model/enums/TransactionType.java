package com.lpdev.financemanagerapi.model.enums;

public enum TransactionType {
    DEPOSIT("deposit"),
    WITHDRAWAL("withdraw");

    private String value;
    TransactionType(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
