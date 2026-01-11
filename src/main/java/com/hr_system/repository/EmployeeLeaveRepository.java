package com.hr_system.repository;

import com.hr_system.entity.EmployeeLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeLeaveRepository extends JpaRepository<EmployeeLeave, UUID> {

    List<EmployeeLeave> findByEmployee_EmployeeId(UUID employeeId);

    List<EmployeeLeave> findByLeaveTypeContainingIgnoreCase(String leaveType);

    List<EmployeeLeave> findByApproved(Boolean approved);

    List<EmployeeLeave> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    boolean existsByEmployee_EmployeeIdAndStartDateAndEndDate(UUID employeeId, LocalDate startDate, LocalDate endDate);

    boolean existsByEmployee_EmployeeIdAndStartDateAndEndDateAndLeaveIdNot(UUID employeeId, LocalDate startDate, LocalDate endDate, UUID leaveId);

    @Query("SELECT el FROM EmployeeLeave el JOIN el.employee e WHERE " +
            "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(el.leaveType) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<EmployeeLeave> searchEmployeeLeaves(@Param("keyword") String keyword);
}