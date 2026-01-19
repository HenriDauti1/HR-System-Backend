package com.hr_system.controller;

import com.hr_system.dto.ActiveContractsDto;
import com.hr_system.dto.PendingLeaveRequestsDto;
import com.hr_system.dto.TotalDepartmentsAndChangeDto;
import com.hr_system.dto.TotalEmployeesAndChangesDto;
import com.hr_system.service.ContractService;
import com.hr_system.service.DepartmentService;
import com.hr_system.service.EmployeeLeaveService;
import com.hr_system.service.EmployeeService;
import com.hr_system.util.ApiResponse;
import com.hr_system.util.ApiResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/stats")
@RequiredArgsConstructor
public class StatisticsController {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final ContractService contractService;
    private final EmployeeLeaveService employeeLeaveService;

    @GetMapping("/total-employees")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<TotalEmployeesAndChangesDto>> getTotalEmployees() {
        TotalEmployeesAndChangesDto totalEmployeesDTO = employeeService.getTotalEmployees();
        return ResponseEntity.status(200).body(ApiResponseUtils.createResponse("success", "Total employees retrieved successfully", totalEmployeesDTO));
    }

    @GetMapping("/total-departments")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<TotalDepartmentsAndChangeDto>> getTotalDepartments() {
        TotalDepartmentsAndChangeDto totalDepartmentsDTO = departmentService.getTotalDepartments();
        return ResponseEntity.status(200).body(ApiResponseUtils.createResponse("success", "Total departments retrieved successfully", totalDepartmentsDTO));
    }

    @GetMapping("/total-contracts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ActiveContractsDto>> getTotalContracts() {
        ActiveContractsDto totalContracts = contractService.getTotalContracts();
        return ResponseEntity.status(200).body(ApiResponseUtils.createResponse("success", "Total contracts retrieved successfully", totalContracts));
    }

    @GetMapping("/total-leaves-today")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PendingLeaveRequestsDto>> getTotalLeavesToday() {
        PendingLeaveRequestsDto pendingLeavesDto = employeeLeaveService.getPendingLeaveRequests();
        return ResponseEntity.status(200).body(ApiResponseUtils.createResponse("success", "Total leaves today retrieved successfully", pendingLeavesDto));
    }
}
