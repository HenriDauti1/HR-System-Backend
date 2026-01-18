package com.hr_system.controller;

import com.hr_system.dto.CountryDTO;
import com.hr_system.service.CountryService;
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
@RequestMapping("/v1/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<CountryDTO>>> getAllCountries() {
        List<CountryDTO> countries = countryService.getAllCountries();
        return ResponseEntity.status(200).body(ApiResponseUtils.createResponse("success", "Countries retrieved successfully", countries));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CountryDTO>> createCountry(@Valid @RequestBody CountryDTO countryDTO) {
        CountryDTO createdCountry = countryService.createCountry(countryDTO);
        return ResponseEntity.status(201).body(ApiResponseUtils.createResponse("success", "Country created successfully", createdCountry));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CountryDTO>> updateCountry(@PathVariable UUID id, @Valid @RequestBody CountryDTO countryDTO) {
        CountryDTO updatedCountry = countryService.updateCountry(id, countryDTO);
        return ResponseEntity.status(200).body(ApiResponseUtils.createResponse("success", "Country updated successfully", updatedCountry));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteCountry(@PathVariable UUID id) {
        countryService.deleteCountry(id);
        return ResponseEntity.status(200).body(ApiResponseUtils.createResponse("success", "Country deleted successfully"));
    }
}