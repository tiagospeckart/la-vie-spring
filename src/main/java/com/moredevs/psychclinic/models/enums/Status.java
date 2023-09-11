package com.moredevs.psychclinic.models.enums;

import lombok.Getter;

@Getter
public enum Status {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private String description = "";

    Status(String description) {
        this.description = description;
    }
}
