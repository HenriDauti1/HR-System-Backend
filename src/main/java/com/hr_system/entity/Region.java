package com.hr_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "region")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Region extends BaseEntity {
    
    @Id
    @GeneratedValue
    @Column(name = "region_id", columnDefinition = "uuid")
    private UUID regionId;
    
    @Column(name = "region_name", nullable = false, unique = true)
    private String regionName;
    
    @Column(name = "description")
    private String description;
    
    @OneToMany(mappedBy = "region")
    private List<Country> countries;
}