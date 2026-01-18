package com.hr_system.repository;

import com.hr_system.entity.EmployeeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeHistoryRepository extends JpaRepository<EmployeeHistory, UUID> {

    List<EmployeeHistory> findAllByEmployee_IsActiveTrue();

    @Query("SELECT eh FROM EmployeeHistory eh WHERE " + "eh.employee.employeeId = :employeeId AND " + "eh.employeeHistoryId <> :excludeId AND " + "eh.employee.isActive = true AND " + "((eh.validFrom <= :validTo AND (eh.validTo IS NULL OR eh.validTo >= :validFrom)))")
    List<EmployeeHistory> findOverlappingHistory(@Param("employeeId") UUID employeeId, @Param("validFrom") OffsetDateTime validFrom, @Param("validTo") OffsetDateTime validTo, @Param("excludeId") UUID excludeId);
}