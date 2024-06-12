package com.jhta2402.jhta2402_3_team_project_1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Grade {
    // 열거형 제한: getter, 속성을 가지는 생성자 하나를 만들면 됨.
    MEMBER("member"),
    MANAGER("manager"),
    ADMIN("admin");

    private final String label;

    /*public String label() {
        return label;
    }*/

    /*Grade(String label) {
        this.label = label;
    }*/
}
