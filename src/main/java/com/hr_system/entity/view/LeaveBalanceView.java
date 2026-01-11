package com.hr_system.entity.view;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "vw_leave_balance")
@Immutable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveBalanceView {

    @Id
    @Column(name = "employee_id")
    private UUID employeeId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "position_name")
    private String positionName;

    @Column(name = "total_allowed_days")
    private Integer totalAllowedDays;

    @Column(name = "paid_days_used")
    private Long paidDaysUsed;

    @Column(name = "sick_days_used")
    private Long sickDaysUsed;

    @Column(name = "unpaid_days_used")
    private Long unpaidDaysUsed;

    @Column(name = "remaining_days")
    private Long remainingDays;

    @Column(name = "scheduled_future_days")
    private Long scheduledFutureDays;

    @Column(name = "balance_status")
    private String balanceStatus;

    @Column(name = "usage_percentage")
    private BigDecimal usagePercentage;
}
