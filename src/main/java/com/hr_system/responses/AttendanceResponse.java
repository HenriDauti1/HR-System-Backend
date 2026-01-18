package com.hr_system.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponse {
    private UUID attendanceId;
    private UUID employeeId;
    private String employeeName;
    private LocalDate attendanceDate;
    private OffsetDateTime checkIn;
    private OffsetDateTime checkOut;
}