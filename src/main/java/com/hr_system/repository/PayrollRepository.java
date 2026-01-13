package com.hr_system.repository;

import com.hr_system.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, UUID> {

    List<Payroll> findByPayrollId(UUID payrollId);

    List<Payroll> findByEmployee_EmployeeId(UUID employeeId);

    @Query("SELECT p FROM Payroll p WHERE " +
            "LOWER(p.employee.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.employee.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Payroll> searchPayrolls(@Param("keyword") String keyword);
}