package com.hr_system.repository;

import com.hr_system.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Repository
public interface CountryRepository extends JpaRepository<Country, UUID> {

    Optional<Country> findByCountryName(String countryName);

    List<Country> findByCountryId(UUID countryId);

    List<Country> findByRegion_RegionId(UUID regionId);

    List<Country> findByRegion_RegionName(String regionName);

    List<Country> findByIsActive(Boolean isActive);

    @Query("SELECT c FROM Country c WHERE " +
            "LOWER(c.countryName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.region.regionName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Country> searchCountries(@Param("keyword") String keyword);
}