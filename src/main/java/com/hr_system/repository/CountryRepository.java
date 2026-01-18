package com.hr_system.repository;

import com.hr_system.entity.Country;
import com.hr_system.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Repository
public interface CountryRepository extends JpaRepository<Country, UUID> {

    @Modifying
    @Transactional
    @Query("""
                delete from Country c
                where c.countryId = :id
            """)
    void delete(@Param("id") UUID id);

    Optional<Country> findByCountryName(String countryName);

    List<Country> findByIsActiveTrue();

    Optional<Country> findByCountryIdAndIsActiveTrue(UUID countryId);
}