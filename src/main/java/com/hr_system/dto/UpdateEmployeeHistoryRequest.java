package com.hr_system.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeHistoryRequest {
    
    @NotNull(message = "Employee is required")
    private UUID employeeId;
    
    @NotNull(message = "Department is required")
    private UUID departmentId;
    
    @NotNull(message = "Position is required")
    private UUID positionId;
    
    @NotNull(message = "Valid From date is required")
    private OffsetDateTime validFrom;
    
    private OffsetDateTime validTo;
}