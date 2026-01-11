package com.hr_system.entity.view;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "vw_attendance_summary_monthly")
@Immutable
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AttendanceSummaryMonthlyView.AttendanceSummaryId.class)
public class AttendanceSummaryMonthlyView {

    @Id
    @Column(name = "employee_id")
    private UUID employeeId;

    @Id
    @Column(name = "month_year")
    private OffsetDateTime monthYear;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "position_name")
    private String positionName;

    @Column(name = "days_worked")
    private Long daysWorked;

    @Column(name = "total_hours_worked")
    private BigDecimal totalHoursWorked;

    @Column(name = "avg_hours_per_day")
    private BigDecimal avgHoursPerDay;

    @Column(name = "late_arrivals")
    private Long lateArrivals;

    @Column(name = "overtime_hours")
    private BigDecimal overtimeHours;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AttendanceSummaryId implements Serializable {
        private UUID employeeId;
        private OffsetDateTime monthYear;
    }
}