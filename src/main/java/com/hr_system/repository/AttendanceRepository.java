package com.hr_system.repository;

import com.hr_system.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {

    List<Attendance> findByEmployee_EmployeeId(UUID employeeId);

    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);

    List<Attendance> findByAttendanceDateBetween(LocalDate startDate, LocalDate endDate);

    boolean existsByEmployee_EmployeeIdAndAttendanceDate(UUID employeeId, LocalDate attendanceDate);

    boolean existsByEmployee_EmployeeIdAndAttendanceDateAndAttendanceIdNot(UUID employeeId, LocalDate attendanceDate, UUID attendanceId);

    @Query("SELECT a FROM Attendance a JOIN a.employee e WHERE " +
            "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(a.attendanceDate AS string) LIKE CONCAT('%', :keyword, '%')")
    List<Attendance> searchAttendances(@Param("keyword") String keyword);
}