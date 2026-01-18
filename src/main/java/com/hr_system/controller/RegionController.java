package com.hr_system.controller;

import com.hr_system.dto.RegionDTO;
import com.hr_system.service.RegionService;
import com.hr_system.util.ApiResponse;
import com.hr_system.util.ApiResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<RegionDTO>>> getAllRegions() {
        List<RegionDTO> regions = regionService.getAllRegions();
        return ResponseEntity.status(200).body(ApiResponseUtils.createResponse("success", "Regions retrieved successfully", regions));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RegionDTO>> createRegion(@RequestBody RegionDTO regionDTO) {
        RegionDTO createdRegion = regionService.createRegion(regionDTO);
        return ResponseEntity.status(201).body(ApiResponseUtils.createResponse("success", "Region created successfully", createdRegion));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RegionDTO>> updateRegion(@PathVariable UUID id, @RequestBody RegionDTO regionDTO) {
        RegionDTO updatedRegion = regionService.updateRegion(id, regionDTO);
        return ResponseEntity.status(200).body(ApiResponseUtils.createResponse("success", "Region updated successfully", updatedRegion));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<RegionDTO>> deleteRegion(@PathVariable UUID id) {
        regionService.deleteRegion(id);
        return ResponseEntity.status(200).body(ApiResponseUtils.createResponse("success", "Region Deleted"));
    }
}