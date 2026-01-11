package com.hr_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "department", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"department_name", "country_id"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Department extends BaseEntity {
    
    @Id
    @GeneratedValue
    @Column(name = "department_id", columnDefinition = "uuid")
    private UUID departmentId;
    
    @Column(name = "department_name", nullable = false)
    private String departmentName;
    
    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;
    
    @OneToMany(mappedBy = "department")
    private List<EmployeeHistory> employeeHistories;
}