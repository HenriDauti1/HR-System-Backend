package com.hr_system.entity.view;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "vw_expiring_contracts")
@Immutable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpiringContractView {

    @Id
    @Column(name = "employee_id")
    private UUID employeeId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "position_name")
    private String positionName;

    @Column(name = "contract_type")
    private String contractType;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "days_until_expiry")
    private Integer daysUntilExpiry;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "urgency_status")
    private String urgencyStatus;

    @Column(name = "contract_duration_years")
    private BigDecimal contractDurationYears;
}
