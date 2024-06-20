package com.ohboon.ohboon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Grade {
    STANDBY("standby"),
    MEMBER("member"),
    MANAGER("manager"),
    ADMIN("admin"),
    RESTRICTED1("restricted1"),
    RESTRICTED2("restricted2"),
    BANNED("banned"),
    DELETED("deleted");
    private final String label;
}
