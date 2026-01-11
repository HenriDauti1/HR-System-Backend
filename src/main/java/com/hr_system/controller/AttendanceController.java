package com.hr_system.controller;

import com.hr_system.dto.AttendanceResponse;
import com.hr_system.dto.CreateAttendanceRequest;
import com.hr_system.dto.UpdateAttendanceRequest;
import com.hr_system.service.AttendanceService;
import com.hr_system.util.ApiResponse;
import com.hr_system.util.ApiResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getAllAttendances(
            @RequestParam(required = false) Map<String, String> filterParams) {
        List<AttendanceResponse> attendances = attendanceService.getAllAttendances(filterParams);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Attendances retrieved successfully", attendances)
        );
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> searchAttendances(
            @RequestParam(required = false) String keyword) {
        List<AttendanceResponse> attendances = attendanceService.searchAttendances(keyword);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Attendances search completed successfully", attendances)
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<AttendanceResponse>> getAttendanceById(@PathVariable UUID id) {
        AttendanceResponse attendance = attendanceService.getAttendanceById(id);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Attendance retrieved successfully", attendance)
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<AttendanceResponse>> createAttendance(
            @Valid @RequestBody CreateAttendanceRequest request) {
        AttendanceResponse attendance = attendanceService.createAttendance(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseUtils.createResponse("success", "Attendance created successfully", attendance)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<AttendanceResponse>> updateAttendance(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateAttendanceRequest request) {
        AttendanceResponse attendance = attendanceService.updateAttendance(id, request);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Attendance updated successfully", attendance)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<Void>> deleteAttendance(@PathVariable UUID id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Attendance deleted successfully")
        );
    }
}