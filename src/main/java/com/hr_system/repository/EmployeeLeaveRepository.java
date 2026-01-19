package com.hr_system.repository;

import com.hr_system.entity.EmployeeLeave;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeLeaveRepository extends JpaRepository<EmployeeLeave, UUID> {

    List<EmployeeLeave> findAllByEmployee_IsActiveTrue(Sort sort);

    boolean existsByEmployee_EmployeeIdAndStartDateAndEndDate(UUID employeeId, LocalDate startDate, LocalDate endDate);

    boolean existsByEmployee_EmployeeIdAndStartDateAndEndDateAndLeaveIdNot(UUID employeeId, LocalDate startDate, LocalDate endDate, UUID leaveId);

    @Query("SELECT COUNT(el) FROM EmployeeLeave el " +
        "WHERE el.approved IS NULL " +
        "AND CAST(el.createdAt AS DATE) = CURRENT_DATE")
    int countPendingLeavesToday();
}