package com.hr_system.controller;

import com.hr_system.dto.CountryDTO;
import com.hr_system.service.CountryService;
import com.hr_system.util.ApiResponse;
import com.hr_system.util.ApiResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<List<CountryDTO>>> getAllCountries(
            @RequestParam(required = false) Map<String, String> filterParams) {
        List<CountryDTO> countries = countryService.getAllCountries(filterParams);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Countries retrieved successfully", countries)
        );
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<List<CountryDTO>>> searchCountries(
            @RequestParam(required = false) String keyword) {
        List<CountryDTO> countries = countryService.searchCountries(keyword);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Countries search completed successfully", countries)
        );
    }
}
