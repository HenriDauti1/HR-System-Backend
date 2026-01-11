package com.hr_system.entity.view;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "vw_department_statistics")
@Immutable
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(DepartmentStatisticsView.DepartmentStatisticsId.class)
public class DepartmentStatisticsView {
    
    @Id
    @Column(name = "region_name")
    private String regionName;
    
    @Id
    @Column(name = "country_name")
    private String countryName;
    
    @Id
    @Column(name = "department_name")
    private String departmentName;
    
    @Column(name = "total_employees")
    private Long totalEmployees;
    
    @Column(name = "male_count")
    private Long maleCount;
    
    @Column(name = "female_count")
    private Long femaleCount;
    
    @Column(name = "avg_years_service")
    private BigDecimal avgYearsService;
    
    @Column(name = "male_percentage")
    private BigDecimal malePercentage;
    
    @Column(name = "female_percentage")
    private BigDecimal femalePercentage;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DepartmentStatisticsId implements Serializable {
        private String regionName;
        private String countryName;
        private String departmentName;
    }
}