package com.hr_system.service;

import com.hr_system.dto.CreateEmployeeLeaveRequest;
import com.hr_system.dto.EmployeeLeaveResponse;
import com.hr_system.dto.UpdateEmployeeLeaveRequest;
import com.hr_system.entity.Employee;
import com.hr_system.entity.EmployeeLeave;
import com.hr_system.exception.DuplicateResourceException;
import com.hr_system.exception.ResourceNotFoundException;
import com.hr_system.repository.EmployeeLeaveRepository;
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
public class EmployeeLeaveService {

    private final EmployeeLeaveRepository employeeLeaveRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public List<EmployeeLeaveResponse> getAllEmployeeLeaves(Map<String, String> filterParams) {
        List<EmployeeLeave> leaves;

        if (filterParams != null && !filterParams.isEmpty()) {
            String leaveId = filterParams.get("leaveId");
            String employeeId = filterParams.get("employeeId");
            String leaveType = filterParams.get("leaveType");
            String approved = filterParams.get("approved");
            String startDate = filterParams.get("startDate");
            String endDate = filterParams.get("endDate");

            if (leaveId != null) {
                leaves = employeeLeaveRepository.findById(UUID.fromString(leaveId))
                        .map(List::of)
                        .orElse(List.of());
            } else if (employeeId != null) {
                leaves = employeeLeaveRepository.findByEmployee_EmployeeId(UUID.fromString(employeeId));
            } else if (leaveType != null) {
                leaves = employeeLeaveRepository.findByLeaveTypeContainingIgnoreCase(leaveType);
            } else if (approved != null) {
                leaves = employeeLeaveRepository.findByApproved(Boolean.parseBoolean(approved));
            } else if (startDate != null && endDate != null) {
                leaves = employeeLeaveRepository.findByStartDateBetween(
                        LocalDate.parse(startDate),
                        LocalDate.parse(endDate)
                );
            } else {
                leaves = employeeLeaveRepository.findAll();
            }
        } else {
            leaves = employeeLeaveRepository.findAll();
        }

        return leaves.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EmployeeLeaveResponse> searchEmployeeLeaves(String keyword) {
        List<EmployeeLeave> leaves;

        if (keyword != null && !keyword.trim().isEmpty()) {
            leaves = employeeLeaveRepository.searchEmployeeLeaves(keyword.trim());
        } else {
            leaves = employeeLeaveRepository.findAll();
        }

        return leaves.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EmployeeLeaveResponse getEmployeeLeaveById(UUID id) {
        EmployeeLeave leave = employeeLeaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Leave not found with id: " + id));
        return convertToResponse(leave);
    }

    @Transactional
    public EmployeeLeaveResponse createEmployeeLeave(CreateEmployeeLeaveRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + request.getEmployeeId()));

        if (employeeLeaveRepository.existsByEmployee_EmployeeIdAndStartDateAndEndDate(
                request.getEmployeeId(), request.getStartDate(), request.getEndDate())) {
            throw new DuplicateResourceException(
                    "Leave request for employee '" + employee.getFirstName() + " " + employee.getLastName() +
                            "' from " + request.getStartDate() + " to " + request.getEndDate() + " already exists");
        }

        EmployeeLeave leave = new EmployeeLeave();
        leave.setEmployee(employee);
        leave.setLeaveType(request.getLeaveType());
        leave.setStartDate(request.getStartDate());
        leave.setEndDate(request.getEndDate());
        leave.setApproved(false);

        EmployeeLeave savedLeave = employeeLeaveRepository.save(leave);
        return convertToResponse(savedLeave);
    }

    @Transactional
    public EmployeeLeaveResponse updateEmployeeLeave(UUID id, UpdateEmployeeLeaveRequest request) {
        EmployeeLeave leave = employeeLeaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Leave not found with id: " + id));

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + request.getEmployeeId()));

        if (employeeLeaveRepository.existsByEmployee_EmployeeIdAndStartDateAndEndDateAndLeaveIdNot(
                request.getEmployeeId(), request.getStartDate(), request.getEndDate(), id)) {
            throw new DuplicateResourceException(
                    "Leave request for employee '" + employee.getFirstName() + " " + employee.getLastName() +
                            "' from " + request.getStartDate() + " to " + request.getEndDate() + " already exists");
        }

        leave.setEmployee(employee);
        leave.setLeaveType(request.getLeaveType());
        leave.setStartDate(request.getStartDate());
        leave.setEndDate(request.getEndDate());

        if (request.getApproved() != null) {
            leave.setApproved(request.getApproved());
        }

        EmployeeLeave updatedLeave = employeeLeaveRepository.save(leave);
        return convertToResponse(updatedLeave);
    }

    @Transactional
    public void deleteEmployeeLeave(UUID id) {
        EmployeeLeave leave = employeeLeaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Leave not found with id: " + id));
        employeeLeaveRepository.delete(leave);
    }

    private EmployeeLeaveResponse convertToResponse(EmployeeLeave leave) {
        EmployeeLeaveResponse response = new EmployeeLeaveResponse();
        response.setLeaveId(leave.getLeaveId());
        response.setEmployeeId(leave.getEmployee().getEmployeeId());
        response.setEmployeeName(leave.getEmployee().getFirstName() + " " + leave.getEmployee().getLastName());
        response.setLeaveType(leave.getLeaveType());
        response.setStartDate(leave.getStartDate());
        response.setEndDate(leave.getEndDate());
        response.setApproved(leave.getApproved());
        return response;
    }
}