package com.hr_system.repository;

import com.hr_system.entity.Employee;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {


    boolean existsByEmail(String email);

    boolean existsByEmailAndEmployeeIdNot(String email, UUID employeeId);

    List<Employee> findByEmailContainingIgnoreCase(String email);

    List<Employee> findAllByIsActiveTrue();

    Optional<Employee> findByEmployeeIdAndIsActiveTrue(UUID employeeId);

    List<Employee> findByFirstNameContainingIgnoreCase(String firstName);

    List<Employee> findByLastNameContainingIgnoreCase(String lastName);

    List<Employee> findByManager_EmployeeId(UUID managerId);

    List<Employee> findByIsActive(Boolean isActive);

    @Query("SELECT e FROM Employee e WHERE " +
            "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.phone) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Employee> searchEmployees(@Param("keyword") String keyword);
}