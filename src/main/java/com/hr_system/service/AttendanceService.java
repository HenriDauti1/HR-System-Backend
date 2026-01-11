package com.hr_system.service;

import com.hr_system.dto.AttendanceResponse;
import com.hr_system.dto.CreateAttendanceRequest;
import com.hr_system.dto.UpdateAttendanceRequest;
import com.hr_system.entity.Attendance;
import com.hr_system.entity.Employee;
import com.hr_system.exception.DuplicateResourceException;
import com.hr_system.exception.ResourceNotFoundException;
import com.hr_system.repository.AttendanceRepository;
import com.hr_system.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public List<AttendanceResponse> getAllAttendances(Map<String, String> filterParams) {
        List<Attendance> attendances;

        if (filterParams != null && !filterParams.isEmpty()) {
            String attendanceId = filterParams.get("attendanceId");
            String employeeId = filterParams.get("employeeId");
            String attendanceDate = filterParams.get("attendanceDate");
            String fromDate = filterParams.get("fromDate");
            String toDate = filterParams.get("toDate");

            if (attendanceId != null) {
                attendances = attendanceRepository.findById(UUID.fromString(attendanceId))
                        .map(List::of)
                        .orElse(List.of());
            } else if (employeeId != null) {
                attendances = attendanceRepository.findByEmployee_EmployeeId(UUID.fromString(employeeId));
            } else if (attendanceDate != null) {
                attendances = attendanceRepository.findByAttendanceDate(LocalDate.parse(attendanceDate));
            } else if (fromDate != null && toDate != null) {
                attendances = attendanceRepository.findByAttendanceDateBetween(
                        LocalDate.parse(fromDate),
                        LocalDate.parse(toDate)
                );
            } else {
                attendances = attendanceRepository.findAll();
            }
        } else {
            attendances = attendanceRepository.findAll();
        }

        return attendances.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AttendanceResponse> searchAttendances(String keyword) {
        List<Attendance> attendances;

        if (keyword != null && !keyword.trim().isEmpty()) {
            attendances = attendanceRepository.searchAttendances(keyword.trim());
        } else {
            attendances = attendanceRepository.findAll();
        }

        return attendances.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AttendanceResponse getAttendanceById(UUID id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with id: " + id));
        return convertToResponse(attendance);
    }

    @Transactional
    public AttendanceResponse createAttendance(CreateAttendanceRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + request.getEmployeeId()));

        if (attendanceRepository.existsByEmployee_EmployeeIdAndAttendanceDate(
                request.getEmployeeId(), request.getAttendanceDate())) {
            throw new DuplicateResourceException(
                    "Attendance for employee '" + employee.getFirstName() + " " + employee.getLastName() +
                            "' on date " + request.getAttendanceDate() + " already exists");
        }

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setAttendanceDate(request.getAttendanceDate());
        attendance.setCheckIn(request.getCheckIn());
        attendance.setCheckOut(request.getCheckOut());

        Attendance savedAttendance = attendanceRepository.save(attendance);
        return convertToResponse(savedAttendance);
    }

    @Transactional
    public AttendanceResponse updateAttendance(UUID id, UpdateAttendanceRequest request) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with id: " + id));

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + request.getEmployeeId()));

        if (attendanceRepository.existsByEmployee_EmployeeIdAndAttendanceDateAndAttendanceIdNot(
                request.getEmployeeId(), request.getAttendanceDate(), id)) {
            throw new DuplicateResourceException(
                    "Attendance for employee '" + employee.getFirstName() + " " + employee.getLastName() +
                            "' on date " + request.getAttendanceDate() + " already exists");
        }

        attendance.setEmployee(employee);
        attendance.setAttendanceDate(request.getAttendanceDate());
        attendance.setCheckIn(request.getCheckIn());
        attendance.setCheckOut(request.getCheckOut());

        Attendance updatedAttendance = attendanceRepository.save(attendance);
        return convertToResponse(updatedAttendance);
    }

    @Transactional
    public void deleteAttendance(UUID id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with id: " + id));
        attendanceRepository.delete(attendance);
    }

    private AttendanceResponse convertToResponse(Attendance attendance) {
        AttendanceResponse response = new AttendanceResponse();
        response.setAttendanceId(attendance.getAttendanceId());
        response.setEmployeeId(attendance.getEmployee().getEmployeeId());
        response.setEmployeeName(attendance.getEmployee().getFirstName() + " " + attendance.getEmployee().getLastName());
        response.setAttendanceDate(attendance.getAttendanceDate());
        response.setCheckIn(attendance.getCheckIn());
        response.setCheckOut(attendance.getCheckOut());
        return response;
    }
}