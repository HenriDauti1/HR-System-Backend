package com.hr_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeHistoryResponse {
    private UUID employeeHistoryId;
    private UUID employeeId;
    private String employeeName;
    private UUID departmentId;
    private String departmentName;
    private UUID positionId;
    private String positionName;
    private OffsetDateTime validFrom;
    private OffsetDateTime validTo;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}