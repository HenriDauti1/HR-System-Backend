package com.hr_system.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActiveEmployeeSortField {
    EMPLOYEE_NAME("fullName"),
    DEPARTMENT("departmentName"),
    LOCATION("regionName"),
    SERVICE("yearsOfService");

    private final String fieldName;
}