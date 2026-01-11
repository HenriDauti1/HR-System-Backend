package com.hr_system.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LeaveBalanceSortField {
    EMPLOYEE("fullName"),
    DEPARTMENT("departmentName"),
    TOTAL_ALLOWED("totalAllowedDays"),
    REMAINING("remainingDays"),
    STATUS("balanceStatus"),
    USAGE_PERCENTAGE("usagePercentage");

    private final String fieldName;
}