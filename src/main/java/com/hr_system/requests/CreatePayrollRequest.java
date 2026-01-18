package com.hr_system.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePayrollRequest {

    @NotNull(message = "Employee ID is required")
    private UUID employeeId;

    @NotNull(message = "Pay period start date is required")
    private LocalDate payPeriodStart;

    @NotNull(message = "Pay period end date is required")
    private LocalDate payPeriodEnd;

    @NotNull(message = "Gross salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Gross salary must be greater than 0")
    private BigDecimal grossSalary;

    @NotNull(message = "Net salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Net salary must be greater than 0")
    private BigDecimal netSalary;

    private OffsetDateTime paidAt;
}