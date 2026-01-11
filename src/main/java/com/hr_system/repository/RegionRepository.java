package com.hr_system.repository;

import com.hr_system.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RegionRepository extends JpaRepository<Region, UUID> {

    List<Region> findByRegionId(UUID regionId);

    List<Region> findByRegionName(String regionName);

    List<Region> findByDescriptionContainingIgnoreCase(String description);

    List<Region> findByIsActive(Boolean isActive);

    @Query("SELECT r FROM Region r WHERE " +
            "LOWER(r.regionName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(r.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Region> searchRegions(@Param("keyword") String keyword);
}