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

    List<EmployeeHistory> findByEmployee_EmployeeId(UUID employeeId);

    List<EmployeeHistory> findByEmployeeHistoryId(UUID employeeHistoryId);

    List<EmployeeHistory> findByDepartment_DepartmentId(UUID departmentId);

    List<EmployeeHistory> findByDepartment_DepartmentName(String departmentName);

    List<EmployeeHistory> findByPosition_PositionId(UUID positionId);

    List<EmployeeHistory> findByPosition_PositionName(String positionName);

    @Query("SELECT eh FROM EmployeeHistory eh WHERE " +
            "LOWER(CONCAT(eh.employee.firstName, ' ', eh.employee.lastName)) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<EmployeeHistory> findByEmployeeNameContaining(@Param("name") String name);

    @Query("SELECT eh FROM EmployeeHistory eh WHERE " +
            "eh.employee.employeeId = :employeeId AND " +
            "eh.employeeHistoryId <> :excludeId AND " +
            "((eh.validFrom <= :validTo AND (eh.validTo IS NULL OR eh.validTo >= :validFrom)))")
    List<EmployeeHistory> findOverlappingHistory(
            @Param("employeeId") UUID employeeId,
            @Param("validFrom") OffsetDateTime validFrom,
            @Param("validTo") OffsetDateTime validTo,
            @Param("excludeId") UUID excludeId
    );

    @Query("SELECT eh FROM EmployeeHistory eh WHERE " +
            "LOWER(CONCAT(eh.employee.firstName, ' ', eh.employee.lastName)) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(eh.department.departmentName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(eh.position.positionName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<EmployeeHistory> searchEmployeeHistories(@Param("keyword") String keyword);
}