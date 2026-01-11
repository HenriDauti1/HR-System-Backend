package com.hr_system.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DepartmentStatisticsSortField {
    DEPARTMENT("departmentName"),
    REGION("regionName"),
    TOTAL_EMPLOYEES("totalEmployees"),
    AVG_SERVICE("avgYearsService");

    private final String fieldName;
}