package com.hr_system.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAttendanceRequest {

    @NotNull(message = "Employee is required")
    private UUID employeeId;

    @NotNull(message = "Attendance date is required")
    private LocalDate attendanceDate;

    private OffsetDateTime checkIn;
    private OffsetDateTime checkOut;
}