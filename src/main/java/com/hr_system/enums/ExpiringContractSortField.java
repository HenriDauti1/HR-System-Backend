package com.hr_system.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExpiringContractSortField {
    EMPLOYEE("fullName"),
    DEPARTMENT("departmentName"),
    CONTRACT_TYPE("contractType"),
    EXPIRY_DATE("endDate"),
    SALARY("salary"),
    STATUS("urgencyStatus");

    private final String fieldName;
}