package com.hr_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalDepartmentsAndChangeDto {
    private int totalDepartments;
    private float changeFromLastYearPercent;
}
