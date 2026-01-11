package com.hr_system.service;

import com.hr_system.dto.CreateEmployeeHistoryRequest;
import com.hr_system.dto.EmployeeHistoryResponse;
import com.hr_system.dto.UpdateEmployeeHistoryRequest;
import com.hr_system.entity.Department;
import com.hr_system.entity.Employee;
import com.hr_system.entity.EmployeeHistory;
import com.hr_system.entity.Position;
import com.hr_system.exception.ResourceNotFoundException;
import com.hr_system.repository.DepartmentRepository;
import com.hr_system.repository.EmployeeHistoryRepository;
import com.hr_system.repository.EmployeeRepository;
import com.hr_system.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeHistoryService {

    private final EmployeeHistoryRepository employeeHistoryRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;

    @Transactional(readOnly = true)
    public List<EmployeeHistoryResponse> getAllEmployeeHistories(Map<String, String> filterParams) {
        List<EmployeeHistory> histories;

        if (filterParams != null && !filterParams.isEmpty()) {
            String employeeHistoryId = filterParams.get("employeeHistoryId");
            String employeeId = filterParams.get("employeeId");
            String employeeName = filterParams.get("employeeName");
            String departmentId = filterParams.get("departmentId");
            String departmentName = filterParams.get("departmentName");
            String positionId = filterParams.get("positionId");
            String positionName = filterParams.get("positionName");

            if (employeeHistoryId != null) {
                histories = employeeHistoryRepository.findByEmployeeHistoryId(UUID.fromString(employeeHistoryId));
            } else if (employeeId != null) {
                histories = employeeHistoryRepository.findByEmployee_EmployeeId(UUID.fromString(employeeId));
            } else if (employeeName != null) {
                histories = employeeHistoryRepository.findByEmployeeNameContaining(employeeName);
            } else if (departmentId != null) {
                histories = employeeHistoryRepository.findByDepartment_DepartmentId(UUID.fromString(departmentId));
            } else if (departmentName != null) {
                histories = employeeHistoryRepository.findByDepartment_DepartmentName(departmentName);
            } else if (positionId != null) {
                histories = employeeHistoryRepository.findByPosition_PositionId(UUID.fromString(positionId));
            } else if (positionName != null) {
                histories = employeeHistoryRepository.findByPosition_PositionName(positionName);
            } else {
                histories = employeeHistoryRepository.findAll();
            }
        } else {
            histories = employeeHistoryRepository.findAll();
        }

        return histories.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EmployeeHistoryResponse> searchEmployeeHistories(String keyword) {
        List<EmployeeHistory> histories;

        if (keyword != null && !keyword.trim().isEmpty()) {
            histories = employeeHistoryRepository.searchEmployeeHistories(keyword.trim());
        } else {
            histories = employeeHistoryRepository.findAll();
        }

        return histories.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EmployeeHistoryResponse getEmployeeHistoryById(UUID id) {
        EmployeeHistory history = employeeHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee history not found with id: " + id));
        return convertToResponse(history);
    }

    @Transactional(readOnly = true)
    public List<EmployeeHistoryResponse> getEmployeeHistoriesByEmployeeId(UUID employeeId) {
        return employeeHistoryRepository.findByEmployee_EmployeeId(employeeId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmployeeHistoryResponse createEmployeeHistory(CreateEmployeeHistoryRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + request.getEmployeeId()));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + request.getDepartmentId()));

        Position position = positionRepository.findById(request.getPositionId())
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with id: " + request.getPositionId()));

        if (request.getValidTo() != null && request.getValidFrom().isAfter(request.getValidTo())) {
            throw new IllegalArgumentException("Valid From date must be before Valid To date");
        }

        OffsetDateTime validTo = request.getValidTo() != null ? request.getValidTo() : OffsetDateTime.now().plusYears(100);
        List<EmployeeHistory> overlapping = employeeHistoryRepository.findOverlappingHistory(
                request.getEmployeeId(),
                request.getValidFrom(),
                validTo,
                UUID.randomUUID()
        );

        if (!overlapping.isEmpty()) {
            throw new IllegalArgumentException("This history record overlaps with existing records for the same employee");
        }

        EmployeeHistory history = new EmployeeHistory();
        history.setEmployee(employee);
        history.setDepartment(department);
        history.setPosition(position);
        history.setValidFrom(request.getValidFrom());
        history.setValidTo(request.getValidTo());

        EmployeeHistory savedHistory = employeeHistoryRepository.save(history);
        return convertToResponse(savedHistory);
    }

    @Transactional
    public EmployeeHistoryResponse updateEmployeeHistory(UUID id, UpdateEmployeeHistoryRequest request) {
        EmployeeHistory history = employeeHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee history not found with id: " + id));

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + request.getEmployeeId()));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + request.getDepartmentId()));

        Position position = positionRepository.findById(request.getPositionId())
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with id: " + request.getPositionId()));

        if (request.getValidTo() != null && request.getValidFrom().isAfter(request.getValidTo())) {
            throw new IllegalArgumentException("Valid From date must be before Valid To date");
        }

        OffsetDateTime validTo = request.getValidTo() != null ? request.getValidTo() : OffsetDateTime.now().plusYears(100);
        List<EmployeeHistory> overlapping = employeeHistoryRepository.findOverlappingHistory(
                request.getEmployeeId(),
                request.getValidFrom(),
                validTo,
                id
        );

        if (!overlapping.isEmpty()) {
            throw new IllegalArgumentException("This history record overlaps with existing records for the same employee");
        }

        history.setEmployee(employee);
        history.setDepartment(department);
        history.setPosition(position);
        history.setValidFrom(request.getValidFrom());
        history.setValidTo(request.getValidTo());

        EmployeeHistory updatedHistory = employeeHistoryRepository.save(history);
        return convertToResponse(updatedHistory);
    }

    @Transactional
    public void deleteEmployeeHistory(UUID id) {
        EmployeeHistory history = employeeHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee history not found with id: " + id));

        employeeHistoryRepository.delete(history);
    }

    @Transactional
    public EmployeeHistoryResponse endEmployeeHistory(UUID id, OffsetDateTime endDate) {
        EmployeeHistory history = employeeHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee history not found with id: " + id));

        if (endDate.isBefore(history.getValidFrom())) {
            throw new IllegalArgumentException("End date cannot be before the start date");
        }

        history.setValidTo(endDate);
        EmployeeHistory updatedHistory = employeeHistoryRepository.save(history);
        return convertToResponse(updatedHistory);
    }

    private EmployeeHistoryResponse convertToResponse(EmployeeHistory history) {
        EmployeeHistoryResponse response = new EmployeeHistoryResponse();
        response.setEmployeeHistoryId(history.getEmployeeHistoryId());
        response.setEmployeeId(history.getEmployee().getEmployeeId());
        response.setEmployeeName(history.getEmployee().getFirstName() + " " + history.getEmployee().getLastName());
        response.setDepartmentId(history.getDepartment().getDepartmentId());
        response.setDepartmentName(history.getDepartment().getDepartmentName());
        response.setPositionId(history.getPosition().getPositionId());
        response.setPositionName(history.getPosition().getPositionName());
        response.setValidFrom(history.getValidFrom());
        response.setValidTo(history.getValidTo());
        response.setCreatedAt(history.getCreatedAt());
        response.setUpdatedAt(history.getUpdatedAt());
        return response;
    }
}
