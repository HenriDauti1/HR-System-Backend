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
import java.util.Map;

@RestController
@RequestMapping("/v1/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<List<RegionDTO>>> getAllRegions(
            @RequestParam(required = false) Map<String, String> filterParams) {
        List<RegionDTO> regions = regionService.getAllRegions(filterParams);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Regions retrieved successfully", regions)
        );
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<List<RegionDTO>>> searchRegions(
            @RequestParam(required = false) String keyword) {
        List<RegionDTO> regions = regionService.searchRegions(keyword);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Regions search completed successfully", regions)
        );
    }
}
