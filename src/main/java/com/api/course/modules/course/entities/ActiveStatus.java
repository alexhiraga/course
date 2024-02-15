package com.api.course.modules.course.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActiveStatus {
    ACTIVE("Ativo"), INACTIVE("Inativo");

    private final String label;
}
