package com.hr_system.service;

import com.hr_system.dto.TotalDepartmentsAndChangeDto;
import com.hr_system.dto.TotalEmployeesAndChangesDto;
import com.hr_system.entity.Country;
import com.hr_system.entity.Department;
import com.hr_system.exception.DuplicateResourceException;
import com.hr_system.exception.ResourceNotFoundException;
import com.hr_system.repository.CountryRepository;
import com.hr_system.repository.DepartmentRepository;
import com.hr_system.requests.CreateDepartmentRequest;
import com.hr_system.requests.UpdateDepartmentRequest;
import com.hr_system.responses.DepartmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CountryRepository countryRepository;

    @Transactional(readOnly = true)
    public List<DepartmentResponse> getAllDepartments() {
        List<Department> departments = departmentRepository.findAllByIsActiveTrue();
        return departments.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Transactional
    public DepartmentResponse createDepartment(CreateDepartmentRequest request) {
        Country country = countryRepository.findByCountryIdAndIsActiveTrue(request.getCountryId()).orElseThrow(() -> new ResourceNotFoundException("Country not found with id: " + request.getCountryId()));

        if (departmentRepository.existsByDepartmentNameAndCountry_CountryIdAndDepartmentIdNot(request.getDepartmentName(), request.getCountryId(), UUID.randomUUID())) {
            throw new DuplicateResourceException("Department with name '" + request.getDepartmentName() + "' already exists in " + country.getCountryName());
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
        Department department = departmentRepository.findByDepartmentIdAndIsActiveTrue(id).orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));

        Country country = countryRepository.findByCountryIdAndIsActiveTrue(request.getCountryId()).orElseThrow(() -> new ResourceNotFoundException("Country not found with id: " + request.getCountryId()));

        if (departmentRepository.existsByDepartmentNameAndCountry_CountryIdAndDepartmentIdNot(request.getDepartmentName(), request.getCountryId(), id)) {
            throw new DuplicateResourceException("Department with name '" + request.getDepartmentName() + "' already exists in " + country.getCountryName());
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
        departmentRepository.findByDepartmentIdAndIsActiveTrue(id).orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        departmentRepository.delete(id);
    }

    public TotalDepartmentsAndChangeDto getTotalDepartments() {
        int totalDepartments = departmentRepository.countByIsActiveTrue();
        int departmentsAddedLastYear= departmentRepository.lastYearCount();
        float percentageChange = totalDepartments == 0 ? 0 : ((float) departmentsAddedLastYear / totalDepartments) * 100;

        TotalDepartmentsAndChangeDto dto = new TotalDepartmentsAndChangeDto();
        dto.setTotalDepartments(totalDepartments);
        dto.setChangeFromLastYearPercent(percentageChange);

        return dto;
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