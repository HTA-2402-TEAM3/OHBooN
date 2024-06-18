package com.jhta2402.jhta2402_3_team_project_1.dto;

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
