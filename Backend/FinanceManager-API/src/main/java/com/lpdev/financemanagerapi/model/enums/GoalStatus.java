package com.lpdev.financemanagerapi.model.enums;

public enum GoalStatus {
    STARTED("started"),
    IN_PROGRESS("in_progress"),
    COMPLETED("completed"),
    PAUSED("paused");

    private String value;

    GoalStatus(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
