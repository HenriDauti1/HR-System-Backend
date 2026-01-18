package com.hr_system.controller;

import com.hr_system.responses.AttendanceResponse;
import com.hr_system.service.AttendanceService;
import com.hr_system.util.ApiResponse;
import com.hr_system.util.ApiResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getAllAttendances() {
        List<AttendanceResponse> attendances = attendanceService.getAllAttendances();
        return ResponseEntity.status(200).body(ApiResponseUtils.createResponse("success", "Attendances retrieved successfully", attendances));
    }
}