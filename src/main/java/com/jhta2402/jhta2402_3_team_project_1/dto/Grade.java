package com.jhta2402.jhta2402_3_team_project_1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Grade {
    MEMBER("member"),
    MANAGER("manager"),
    ADMIN("admin");

    private final String label;
}
