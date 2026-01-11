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
@Table(name = "vw_active_employees")
@Immutable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveEmployeeView {
    
    @Id
    @Column(name = "employee_id")
    private UUID employeeId;
    
    @Column(name = "full_name")
    private String fullName;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "department_name")
    private String departmentName;
    
    @Column(name = "position_name")
    private String positionName;
    
    @Column(name = "country_name")
    private String countryName;
    
    @Column(name = "region_name")
    private String regionName;
    
    @Column(name = "hire_date")
    private LocalDate hireDate;
    
    @Column(name = "years_of_service")
    private BigDecimal yearsOfService;
}