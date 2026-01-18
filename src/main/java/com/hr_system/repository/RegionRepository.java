package com.hr_system.repository;

import com.hr_system.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegionRepository extends JpaRepository<Region, UUID> {
    @Modifying
    @Transactional
    @Query("""
                delete from Region r
                where r.regionId = :id
            """)
    void delete(@Param("id") UUID id);

    List<Region> findByIsActiveTrue();

    Optional<Region> findByRegionIdAndIsActiveTrue(UUID regionId);
}