package com.lpdev.financemanagerapi.model.enums;

public enum GoalPriority {
    HIGH("high"),
    MEDIUM("medium"),
    LOW("low"),
    NO_PRIORITY("low_priority");

    private String value;

    GoalPriority(String value){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
}
