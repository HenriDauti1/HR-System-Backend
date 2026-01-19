package com.hr_system.service;

import com.hr_system.dto.TotalEmployeesAndChangesDto;
import com.hr_system.entity.Employee;
import com.hr_system.exception.DuplicateResourceException;
import com.hr_system.exception.ResourceNotFoundException;
import com.hr_system.repository.EmployeeRepository;
import com.hr_system.requests.CreateEmployeeRequest;
import com.hr_system.requests.UpdateEmployeeRequest;
import com.hr_system.responses.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;


    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployees(Map<String, String> filterParams) {
        List<Employee> employees;

        if (filterParams != null && !filterParams.isEmpty()) {
            String employeeId = filterParams.get("employeeId");
            String email = filterParams.get("email");
            String firstName = filterParams.get("firstName");
            String lastName = filterParams.get("lastName");
            String managerId = filterParams.get("managerId");
            String isActive = filterParams.get("isActive");

            if (employeeId != null) {
                employees = employeeRepository.findById(UUID.fromString(employeeId)).map(List::of).orElse(List.of());
            } else if (email != null) {
                employees = employeeRepository.findByEmailContainingIgnoreCase(email);
            } else if (firstName != null) {
                employees = employeeRepository.findByFirstNameContainingIgnoreCase(firstName);
            } else if (lastName != null) {
                employees = employeeRepository.findByLastNameContainingIgnoreCase(lastName);
            } else if (managerId != null) {
                employees = employeeRepository.findByManager_EmployeeId(UUID.fromString(managerId));
            } else if (isActive != null) {
                employees = employeeRepository.findByIsActive(Boolean.parseBoolean(isActive));
            } else {
                employees = employeeRepository.findAll();
            }
        } else {
            employees = employeeRepository.findAllByIsActiveTrue();
        }

        return employees.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Transactional
    public EmployeeResponse createEmployee(CreateEmployeeRequest request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Employee with email '" + request.getEmail() + "' already exists");
        }

        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDateOfBirth(request.getDateOfBirth());
        employee.setGender(request.getGender());
        employee.setNationality(request.getNationality());
        employee.setAddressLine1(request.getAddressLine1());
        employee.setAddressLine2(request.getAddressLine2());
        employee.setCity(request.getCity());
        employee.setState(request.getState());
        employee.setPostalCode(request.getPostalCode());
        employee.setHireDate(request.getHireDate());
        employee.setEmergencyContactName(request.getEmergencyContactName());
        employee.setEmergencyContactPhone(request.getEmergencyContactPhone());
        employee.setEmergencyContactRelationship(request.getEmergencyContactRelationship());
        employee.setIsActive(true);

        if (request.getManagerId() != null) {
            Employee manager = employeeRepository.findById(request.getManagerId()).orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + request.getManagerId()));
            employee.setManager(manager);
        }

        Employee savedEmployee = employeeRepository.save(employee);
        return convertToResponse(savedEmployee);
    }

    @Transactional
    public EmployeeResponse updateEmployee(UUID id, UpdateEmployeeRequest request) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        if (employeeRepository.existsByEmailAndEmployeeIdNot(request.getEmail(), id)) {
            throw new DuplicateResourceException("Employee with email '" + request.getEmail() + "' already exists");
        }

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDateOfBirth(request.getDateOfBirth());
        employee.setGender(request.getGender());
        employee.setNationality(request.getNationality());
        employee.setAddressLine1(request.getAddressLine1());
        employee.setAddressLine2(request.getAddressLine2());
        employee.setCity(request.getCity());
        employee.setState(request.getState());
        employee.setPostalCode(request.getPostalCode());
        employee.setHireDate(request.getHireDate());
        employee.setTerminationDate(request.getTerminationDate());
        employee.setEmergencyContactName(request.getEmergencyContactName());
        employee.setEmergencyContactPhone(request.getEmergencyContactPhone());
        employee.setEmergencyContactRelationship(request.getEmergencyContactRelationship());

        if (request.getIsActive() != null) {
            employee.setIsActive(request.getIsActive());
        }

        if (request.getManagerId() != null) {
            Employee manager = employeeRepository.findById(request.getManagerId()).orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + request.getManagerId()));
            employee.setManager(manager);
        }

        Employee updatedEmployee = employeeRepository.save(employee);
        return convertToResponse(updatedEmployee);
    }

    @Transactional
    public void deleteEmployee(UUID id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        employee.setIsActive(false);
        employeeRepository.save(employee);
    }

    public TotalEmployeesAndChangesDto getTotalEmployees(){
        int totalEmployees = employeeRepository.countByIsActiveTrue();
        int newHiresLastMonth = employeeRepository.lastMonthCount();
        float percentageChange = totalEmployees == 0 ? 0 : ((float) newHiresLastMonth / totalEmployees) * 100;
        TotalEmployeesAndChangesDto dto = new TotalEmployeesAndChangesDto();
        dto.setTotalEmployees(totalEmployees);
        dto.setChangeFromLastMonthPercent(percentageChange);
        return dto;
    }

    private EmployeeResponse convertToResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.setEmployeeId(employee.getEmployeeId());
        response.setFirstName(employee.getFirstName());
        response.setLastName(employee.getLastName());
        response.setEmail(employee.getEmail());
        response.setPhone(employee.getPhone());
        response.setDateOfBirth(employee.getDateOfBirth());
        response.setGender(employee.getGender());
        response.setNationality(employee.getNationality());
        response.setAddressLine1(employee.getAddressLine1());
        response.setAddressLine2(employee.getAddressLine2());
        response.setCity(employee.getCity());
        response.setState(employee.getState());
        response.setPostalCode(employee.getPostalCode());
        response.setHireDate(employee.getHireDate());
        response.setTerminationDate(employee.getTerminationDate());
        response.setEmergencyContactName(employee.getEmergencyContactName());
        response.setEmergencyContactPhone(employee.getEmergencyContactPhone());
        response.setEmergencyContactRelationship(employee.getEmergencyContactRelationship());
        response.setIsActive(employee.getIsActive());

        if (employee.getManager() != null) {
            response.setManagerId(employee.getManager().getEmployeeId());
            response.setManagerName(employee.getManager().getFirstName() + " " + employee.getManager().getLastName());
        }

        return response;
    }
}