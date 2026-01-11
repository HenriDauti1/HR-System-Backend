package com.hr_system.service;

import com.hr_system.entity.view.*;
import com.hr_system.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class HRViewService {

    private final ActiveEmployeeViewRepository activeEmployeeViewRepository;
    private final DepartmentStatisticsViewRepository departmentStatisticsViewRepository;
    private final AttendanceSummaryMonthlyViewRepository attendanceSummaryMonthlyViewRepository;
    private final ExpiringContractViewRepository expiringContractViewRepository;
    private final LeaveBalanceViewRepository leaveBalanceViewRepository;

    public List<ActiveEmployeeView> getAllActiveEmployees(Sort sort) {
        log.info("Fetching all active employees with sort: {}", sort);
        return activeEmployeeViewRepository.findAll(sort);
    }

    public List<DepartmentStatisticsView> getAllDepartmentStatistics(Sort sort) {
        log.info("Fetching all department statistics with sort: {}", sort);
        return departmentStatisticsViewRepository.findAll(sort);
    }

    public List<AttendanceSummaryMonthlyView> getAllAttendanceSummaryMonthly(Sort sort) {
        log.info("Fetching all attendance summary monthly with sort: {}", sort);
        return attendanceSummaryMonthlyViewRepository.findAll(sort);
    }

    public List<ExpiringContractView> getAllExpiringContracts(Sort sort) {
        log.info("Fetching all expiring contracts with sort: {}", sort);
        return expiringContractViewRepository.findAll(sort);
    }

    public List<LeaveBalanceView> getAllLeaveBalances(Sort sort) {
        log.info("Fetching all leave balances with sort: {}", sort);
        return leaveBalanceViewRepository.findAll(sort);
    }
}