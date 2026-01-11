package com.hr_system.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AttendanceSummarySortField {
    EMPLOYEE("fullName"),
    DEPARTMENT("departmentName"),
    DAYS_WORKED("daysWorked"),
    TOTAL_HOURS("totalHoursWorked"),
    AVG_PER_DAY("avgHoursPerDay"),
    LATE_ARRIVALS("lateArrivals"),
    OVERTIME("overtimeHours");

    private final String fieldName;
}