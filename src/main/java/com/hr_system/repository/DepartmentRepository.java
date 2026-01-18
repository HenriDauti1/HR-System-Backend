package com.hr_system.repository;

import com.hr_system.entity.Department;
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
public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    @Modifying
    @Transactional
    @Query("""
                delete from Department r
                where r.departmentId = :id
            """)
    void delete(@Param("id") UUID id);

    boolean existsByDepartmentNameAndCountry_CountryIdAndDepartmentIdNot(String departmentName, UUID countryId, UUID departmentId);

    Optional<Department> findByDepartmentIdAndIsActiveTrue(UUID departmentId);

    List<Department> findAllByIsActiveTrue();
}