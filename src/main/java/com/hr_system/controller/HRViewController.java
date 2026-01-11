package com.hr_system.controller;

import com.hr_system.entity.view.*;
import com.hr_system.enums.*;
import com.hr_system.service.HRViewService;
import com.hr_system.util.ApiResponse;
import com.hr_system.util.ApiResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class HRViewController {

    private final HRViewService hrViewService;

    @GetMapping("/active-employees")
    public ResponseEntity<ApiResponse<List<ActiveEmployeeView>>> getAllActiveEmployees(
            @RequestParam(required = false, defaultValue = "EMPLOYEE_NAME") ActiveEmployeeSortField orderBy,
            @RequestParam(required = false, defaultValue = "ASC") SortDirection direction
    ) {
        Sort sort = Sort.by(direction.getDirection(), orderBy.getFieldName());
        List<ActiveEmployeeView> employees = hrViewService.getAllActiveEmployees(sort);

        ApiResponse<List<ActiveEmployeeView>> response = ApiResponseUtils.createResponse(
                "success",
                "Active employees retrieved successfully",
                employees
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/department-statistics")
    public ResponseEntity<ApiResponse<List<DepartmentStatisticsView>>> getAllDepartmentStatistics(
            @RequestParam(required = false, defaultValue = "DEPARTMENT") DepartmentStatisticsSortField orderBy,
            @RequestParam(required = false, defaultValue = "ASC") SortDirection direction
    ) {
        Sort sort = Sort.by(direction.getDirection(), orderBy.getFieldName());
        List<DepartmentStatisticsView> statistics = hrViewService.getAllDepartmentStatistics(sort);

        ApiResponse<List<DepartmentStatisticsView>> response = ApiResponseUtils.createResponse(
                "success",
                "Department statistics retrieved successfully",
                statistics
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/attendance-summary-monthly")
    public ResponseEntity<ApiResponse<List<AttendanceSummaryMonthlyView>>> getAllAttendanceSummaryMonthly(
            @RequestParam(required = false, defaultValue = "EMPLOYEE") AttendanceSummarySortField orderBy,
            @RequestParam(required = false, defaultValue = "ASC") SortDirection direction
    ) {
        Sort sort = Sort.by(direction.getDirection(), orderBy.getFieldName());
        List<AttendanceSummaryMonthlyView> attendanceSummary = hrViewService.getAllAttendanceSummaryMonthly(sort);

        ApiResponse<List<AttendanceSummaryMonthlyView>> response = ApiResponseUtils.createResponse(
                "success",
                "Attendance summary monthly retrieved successfully",
                attendanceSummary
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/expiring-contracts")
    public ResponseEntity<ApiResponse<List<ExpiringContractView>>> getAllExpiringContracts(
            @RequestParam(required = false, defaultValue = "EXPIRY_DATE") ExpiringContractSortField orderBy,
            @RequestParam(required = false, defaultValue = "ASC") SortDirection direction
    ) {
        Sort sort = Sort.by(direction.getDirection(), orderBy.getFieldName());
        List<ExpiringContractView> expiringContracts = hrViewService.getAllExpiringContracts(sort);

        ApiResponse<List<ExpiringContractView>> response = ApiResponseUtils.createResponse(
                "success",
                "Expiring contracts retrieved successfully",
                expiringContracts
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/leave-balances")
    public ResponseEntity<ApiResponse<List<LeaveBalanceView>>> getAllLeaveBalances(
            @RequestParam(required = false, defaultValue = "EMPLOYEE") LeaveBalanceSortField orderBy,
            @RequestParam(required = false, defaultValue = "ASC") SortDirection direction
    ) {
        Sort sort = Sort.by(direction.getDirection(), orderBy.getFieldName());
        List<LeaveBalanceView> leaveBalances = hrViewService.getAllLeaveBalances(sort);

        ApiResponse<List<LeaveBalanceView>> response = ApiResponseUtils.createResponse(
                "success",
                "Leave balances retrieved successfully",
                leaveBalances
        );
        return ResponseEntity.ok(response);
    }
}