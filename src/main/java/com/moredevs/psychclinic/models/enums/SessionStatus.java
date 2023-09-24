package com.moredevs.psychclinic.models.enums;

import lombok.Getter;

@Getter
public enum SessionStatus {

    COMPLETED("COMPLETED"),
    PLANNED("PLANNED"),
    CANCELLED("CANCELLED");

    private String description = "";

    SessionStatus(String description) {
        this.description = description;
    }

}
