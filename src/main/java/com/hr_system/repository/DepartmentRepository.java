package com.hr_system.repository;

import com.hr_system.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {

    boolean existsByDepartmentNameAndCountry_CountryIdAndDepartmentIdNot(
            String departmentName, UUID countryId, UUID departmentId);

    List<Department> findByDepartmentId(UUID departmentId);

    List<Department> findByDepartmentNameContainingIgnoreCase(String departmentName);

    List<Department> findByCountry_CountryId(UUID countryId);

    List<Department> findByCountry_CountryName(String countryName);

    List<Department> findByIsActive(Boolean isActive);

    @Query("SELECT d FROM Department d WHERE " +
            "LOWER(d.departmentName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(d.country.countryName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Department> searchDepartments(@Param("keyword") String keyword);
}