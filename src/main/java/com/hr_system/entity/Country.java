package com.hr_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "country")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Country extends BaseEntity {
    
    @Id
    @GeneratedValue
    @Column(name = "country_id", columnDefinition = "uuid")
    private UUID countryId;
    
    @Column(name = "country_name", nullable = false, unique = true)
    private String countryName;
    
    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;
    
    @OneToMany(mappedBy = "country")
    private List<Department> departments;
}