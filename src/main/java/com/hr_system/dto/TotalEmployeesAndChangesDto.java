package com.hr_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalEmployeesAndChangesDto {
    private int totalEmployees;
    private float changeFromLastMonthPercent;
}
