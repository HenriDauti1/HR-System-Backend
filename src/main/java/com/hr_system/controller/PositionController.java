package com.hr_system.controller;

import com.hr_system.requests.CreatePositionRequest;
import com.hr_system.requests.UpdatePositionRequest;
import com.hr_system.responses.PositionResponse;
import com.hr_system.service.PositionService;
import com.hr_system.util.ApiResponse;
import com.hr_system.util.ApiResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<PositionResponse>>> getAllPositions() {
        List<PositionResponse> positions = positionService.getAllPositions();
        return ResponseEntity.status(200).body(ApiResponseUtils.createResponse("success", "Positions retrieved successfully", positions));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PositionResponse>> createPosition(@Valid @RequestBody CreatePositionRequest request) {
        PositionResponse position = positionService.createPosition(request);
        return ResponseEntity.status(201).body(ApiResponseUtils.createResponse("success", "Position created successfully", position));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PositionResponse>> updatePosition(@PathVariable UUID id, @Valid @RequestBody UpdatePositionRequest request) {
        PositionResponse position = positionService.updatePosition(id, request);
        return ResponseEntity.status(200).body(ApiResponseUtils.createResponse("success", "Position updated successfully", position));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deletePosition(@PathVariable UUID id) {
        positionService.deletePosition(id);
        return ResponseEntity.status(200).body(ApiResponseUtils.createResponse("success", "Position deleted successfully"));
    }
}