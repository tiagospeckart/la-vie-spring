package com.moredevs.psychclinic.models.enums;

import lombok.Getter;

@Getter
public enum SessionStatus {

    COMPLETED("ACTIVE"),
    PLANNED("INACTIVE"),
    CANCELLED("CANCELLED");

    private String description = "";

    SessionStatus(String description) {
        this.description = description;
    }

}
