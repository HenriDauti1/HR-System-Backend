package com.hr_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "position")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Position extends BaseEntity {
    
    @Id
    @GeneratedValue
    @Column(name = "position_id", columnDefinition = "uuid")
    private UUID positionId;
    
    @Column(name = "position_name", nullable = false, unique = true)
    private String positionName;
    
    @OneToMany(mappedBy = "position")
    private List<EmployeeHistory> employeeHistories;
}