package com.hr_system.controller;

import com.hr_system.dto.CreatePositionRequest;
import com.hr_system.dto.PositionResponse;
import com.hr_system.dto.UpdatePositionRequest;
import com.hr_system.service.PositionService;
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
@RequestMapping("/v1/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @GetMapping
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<List<PositionResponse>>> getAllPositions(
            @RequestParam(required = false) Map<String, String> filterParams) {
        List<PositionResponse> positions = positionService.getAllPositions(filterParams);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Positions retrieved successfully", positions)
        );
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<List<PositionResponse>>> searchPositions(
            @RequestParam(required = false) String keyword) {
        List<PositionResponse> positions = positionService.searchPositions(keyword);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Positions search completed successfully", positions)
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<PositionResponse>> getPositionById(@PathVariable UUID id) {
        PositionResponse position = positionService.getPositionById(id);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Position retrieved successfully", position)
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<PositionResponse>> createPosition(
            @Valid @RequestBody CreatePositionRequest request) {
        PositionResponse position = positionService.createPosition(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseUtils.createResponse("success", "Position created successfully", position)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<PositionResponse>> updatePosition(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePositionRequest request) {
        PositionResponse position = positionService.updatePosition(id, request);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Position updated successfully", position)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<Void>> deletePosition(@PathVariable UUID id) {
        positionService.deletePosition(id);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Position deleted successfully")
        );
    }
}
