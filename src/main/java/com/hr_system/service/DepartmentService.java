package com.hr_system.service;

import com.hr_system.dto.CreateDepartmentRequest;
import com.hr_system.dto.DepartmentResponse;
import com.hr_system.dto.UpdateDepartmentRequest;
import com.hr_system.entity.Country;
import com.hr_system.entity.Department;
import com.hr_system.exception.DuplicateResourceException;
import com.hr_system.exception.ResourceNotFoundException;
import com.hr_system.repository.CountryRepository;
import com.hr_system.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CountryRepository countryRepository;

    @Transactional(readOnly = true)
    public List<DepartmentResponse> getAllDepartments(Map<String, String> filterParams) {
        List<Department> departments;

        if (filterParams != null && !filterParams.isEmpty()) {
            String departmentId = filterParams.get("departmentId");
            String departmentName = filterParams.get("departmentName");
            String countryId = filterParams.get("countryId");
            String countryName = filterParams.get("countryName");
            String isActive = filterParams.get("isActive");

            if (departmentId != null) {
                departments = departmentRepository.findByDepartmentId(UUID.fromString(departmentId));
            } else if (departmentName != null) {
                departments = departmentRepository.findByDepartmentNameContainingIgnoreCase(departmentName);
            } else if (countryId != null) {
                departments = departmentRepository.findByCountry_CountryId(UUID.fromString(countryId));
            } else if (countryName != null) {
                departments = departmentRepository.findByCountry_CountryName(countryName);
            } else if (isActive != null) {
                departments = departmentRepository.findByIsActive(Boolean.parseBoolean(isActive));
            } else {
                departments = departmentRepository.findAll();
            }
        } else {
            departments = departmentRepository.findAll();
        }

        return departments.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DepartmentResponse> searchDepartments(String keyword) {
        List<Department> departments;

        if (keyword != null && !keyword.trim().isEmpty()) {
            departments = departmentRepository.searchDepartments(keyword.trim());
        } else {
            departments = departmentRepository.findAll();
        }

        return departments.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DepartmentResponse getDepartmentById(UUID id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        return convertToResponse(department);
    }

    @Transactional
    public DepartmentResponse createDepartment(CreateDepartmentRequest request) {
        Country country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with id: " + request.getCountryId()));

        if (departmentRepository.existsByDepartmentNameAndCountry_CountryIdAndDepartmentIdNot(
                request.getDepartmentName(), request.getCountryId(), UUID.randomUUID())) {
            throw new DuplicateResourceException(
                    "Department with name '" + request.getDepartmentName() +
                            "' already exists in " + country.getCountryName());
        }

        Department department = new Department();
        department.setDepartmentName(request.getDepartmentName());
        department.setCountry(country);
        department.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);

        Department savedDepartment = departmentRepository.save(department);
        return convertToResponse(savedDepartment);
    }

    @Transactional
    public DepartmentResponse updateDepartment(UUID id, UpdateDepartmentRequest request) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));

        Country country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with id: " + request.getCountryId()));

        if (departmentRepository.existsByDepartmentNameAndCountry_CountryIdAndDepartmentIdNot(
                request.getDepartmentName(), request.getCountryId(), id)) {
            throw new DuplicateResourceException(
                    "Department with name '" + request.getDepartmentName() +
                            "' already exists in " + country.getCountryName());
        }

        department.setDepartmentName(request.getDepartmentName());
        department.setCountry(country);
        if (request.getIsActive() != null) {
            department.setIsActive(request.getIsActive());
        }

        Department updatedDepartment = departmentRepository.save(department);
        return convertToResponse(updatedDepartment);
    }

    @Transactional
    public void deleteDepartment(UUID id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));

        department.setIsActive(false);
        departmentRepository.save(department);
    }

    private DepartmentResponse convertToResponse(Department department) {
        DepartmentResponse response = new DepartmentResponse();
        response.setDepartmentId(department.getDepartmentId());
        response.setDepartmentName(department.getDepartmentName());
        response.setCountryId(department.getCountry().getCountryId());
        response.setCountryName(department.getCountry().getCountryName());
        response.setIsActive(department.getIsActive());
        response.setCreatedAt(department.getCreatedAt());
        response.setUpdatedAt(department.getUpdatedAt());
        return response;
    }
}