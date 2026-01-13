package com.hr_system.service;

import com.hr_system.dto.CreatePayrollRequest;
import com.hr_system.dto.PayrollResponse;
import com.hr_system.dto.UpdatePayrollRequest;
import com.hr_system.entity.Employee;
import com.hr_system.entity.Payroll;
import com.hr_system.exception.ResourceNotFoundException;
import com.hr_system.repository.EmployeeRepository;
import com.hr_system.repository.PayrollRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayrollService {

    private final PayrollRepository payrollRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public List<PayrollResponse> getAllPayrolls(Map<String, String> filterParams) {
        List<Payroll> payrolls;

        if (filterParams != null && !filterParams.isEmpty()) {
            String payrollId = filterParams.get("payrollId");
            String employeeId = filterParams.get("employeeId");

            if (payrollId != null) {
                payrolls = payrollRepository.findByPayrollId(UUID.fromString(payrollId));
            } else if (employeeId != null) {
                payrolls = payrollRepository.findByEmployee_EmployeeId(UUID.fromString(employeeId));
            } else {
                payrolls = payrollRepository.findAll();
            }
        } else {
            payrolls = payrollRepository.findAll();
        }

        return payrolls.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PayrollResponse> searchPayrolls(String keyword) {
        List<Payroll> payrolls;

        if (keyword != null && !keyword.trim().isEmpty()) {
            payrolls = payrollRepository.searchPayrolls(keyword.trim());
        } else {
            payrolls = payrollRepository.findAll();
        }

        return payrolls.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PayrollResponse getPayrollById(UUID id) {
        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payroll not found with id: " + id));
        return convertToResponse(payroll);
    }

    @Transactional
    public PayrollResponse createPayroll(CreatePayrollRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + request.getEmployeeId()));

        if (!request.getPayPeriodEnd().isAfter(request.getPayPeriodStart())) {
            throw new IllegalArgumentException("Pay period end date must be after start date");
        }

        Payroll payroll = new Payroll();
        payroll.setEmployee(employee);
        payroll.setPayPeriodStart(request.getPayPeriodStart());
        payroll.setPayPeriodEnd(request.getPayPeriodEnd());
        payroll.setGrossSalary(request.getGrossSalary());
        payroll.setNetSalary(request.getNetSalary());
        payroll.setPaidAt(request.getPaidAt());

        Payroll savedPayroll = payrollRepository.save(payroll);
        return convertToResponse(savedPayroll);
    }

    @Transactional
    public PayrollResponse updatePayroll(UUID id, UpdatePayrollRequest request) {
        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payroll not found with id: " + id));

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + request.getEmployeeId()));

        if (!request.getPayPeriodEnd().isAfter(request.getPayPeriodStart())) {
            throw new IllegalArgumentException("Pay period end date must be after start date");
        }

        payroll.setEmployee(employee);
        payroll.setPayPeriodStart(request.getPayPeriodStart());
        payroll.setPayPeriodEnd(request.getPayPeriodEnd());
        payroll.setGrossSalary(request.getGrossSalary());
        payroll.setNetSalary(request.getNetSalary());
        payroll.setPaidAt(request.getPaidAt());

        Payroll updatedPayroll = payrollRepository.save(payroll);
        return convertToResponse(updatedPayroll);
    }

    @Transactional
    public void deletePayroll(UUID id) {
        Payroll payroll = payrollRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payroll not found with id: " + id));
        payrollRepository.delete(payroll);
    }

    private PayrollResponse convertToResponse(Payroll payroll) {
        PayrollResponse response = new PayrollResponse();
        response.setPayrollId(payroll.getPayrollId());
        response.setEmployeeId(payroll.getEmployee().getEmployeeId());

        Employee emp = payroll.getEmployee();
        String fullName = emp.getFirstName() + " " + emp.getLastName();
        response.setEmployeeName(fullName);

        response.setPayPeriodStart(payroll.getPayPeriodStart());
        response.setPayPeriodEnd(payroll.getPayPeriodEnd());
        response.setGrossSalary(payroll.getGrossSalary());
        response.setNetSalary(payroll.getNetSalary());
        response.setPaidAt(payroll.getPaidAt());
        response.setCreatedAt(payroll.getCreatedAt());
        response.setUpdatedAt(payroll.getUpdatedAt());
        return response;
    }
}