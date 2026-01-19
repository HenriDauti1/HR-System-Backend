package com.hr_system.service;

import com.hr_system.dto.PendingLeaveRequestsDto;
import com.hr_system.entity.Employee;
import com.hr_system.entity.EmployeeLeave;
import com.hr_system.exception.DuplicateResourceException;
import com.hr_system.exception.ResourceNotFoundException;
import com.hr_system.repository.EmployeeLeaveRepository;
import com.hr_system.repository.EmployeeRepository;
import com.hr_system.requests.CreateEmployeeLeaveRequest;
import com.hr_system.requests.UpdateEmployeeLeaveRequest;
import com.hr_system.responses.EmployeeLeaveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeLeaveService {

    private final EmployeeLeaveRepository employeeLeaveRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public List<EmployeeLeaveResponse> getAllEmployeeLeaves() {
        List<EmployeeLeave> leaves = employeeLeaveRepository.findAllByEmployee_IsActiveTrue(Sort.by(Sort.Direction.DESC, "startDate"));
        return leaves.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Transactional
    public EmployeeLeaveResponse createEmployeeLeave(CreateEmployeeLeaveRequest request) {
        Employee employee = employeeRepository.findByEmployeeIdAndIsActiveTrue(request.getEmployeeId()).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + request.getEmployeeId()));

        if (employeeLeaveRepository.existsByEmployee_EmployeeIdAndStartDateAndEndDate(request.getEmployeeId(), request.getStartDate(), request.getEndDate())) {
            throw new DuplicateResourceException("Leave request for employee '" + employee.getFirstName() + " " + employee.getLastName() + "' from " + request.getStartDate() + " to " + request.getEndDate() + " already exists");
        }

        EmployeeLeave leave = new EmployeeLeave();
        leave.setEmployee(employee);
        leave.setLeaveType(request.getLeaveType());
        leave.setStartDate(request.getStartDate());
        leave.setEndDate(request.getEndDate());
        leave.setApproved(request.getApproved());

        EmployeeLeave savedLeave = employeeLeaveRepository.save(leave);
        return convertToResponse(savedLeave);
    }

    @Transactional
    public EmployeeLeaveResponse updateEmployeeLeave(UUID id, UpdateEmployeeLeaveRequest request) {
        EmployeeLeave leave = employeeLeaveRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee Leave not found with id: " + id));

        Employee employee = employeeRepository.findByEmployeeIdAndIsActiveTrue(request.getEmployeeId()).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + request.getEmployeeId()));

        if (employeeLeaveRepository.existsByEmployee_EmployeeIdAndStartDateAndEndDateAndLeaveIdNot(request.getEmployeeId(), request.getStartDate(), request.getEndDate(), id)) {
            throw new DuplicateResourceException("Leave request for employee '" + employee.getFirstName() + " " + employee.getLastName() + "' from " + request.getStartDate() + " to " + request.getEndDate() + " already exists");
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
    public PendingLeaveRequestsDto getPendingLeaveRequests() {
        int pendingCount = employeeLeaveRepository.countPendingLeavesToday();
        return new PendingLeaveRequestsDto(pendingCount);
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