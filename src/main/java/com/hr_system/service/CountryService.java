package com.hr_system.service;

import com.hr_system.dto.CountryDTO;
import com.hr_system.entity.Country;
import com.hr_system.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    @Transactional(readOnly = true)
    public List<CountryDTO> getAllCountries(Map<String, String> filterParams) {
        List<Country> countries;

        if (filterParams != null && !filterParams.isEmpty()) {
            String countryId = filterParams.get("countryId");
            String countryName = filterParams.get("countryName");
            String regionId = filterParams.get("regionId");
            String regionName = filterParams.get("regionName");
            String isActive = filterParams.get("isActive");

            if (countryId != null) {
                countries = countryRepository.findByCountryId(UUID.fromString(countryId));
            } else if (countryName != null) {
                countries = countryRepository.findByCountryName(countryName)
                        .map(List::of)
                        .orElse(List.of());
            } else if (regionId != null) {
                countries = countryRepository.findByRegion_RegionId(UUID.fromString(regionId));
            } else if (regionName != null) {
                countries = countryRepository.findByRegion_RegionName(regionName);
            } else if (isActive != null) {
                countries = countryRepository.findByIsActive(Boolean.parseBoolean(isActive));
            } else {
                countries = countryRepository.findAll();
            }
        } else {
            countries = countryRepository.findAll();
        }

        return countries.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CountryDTO> searchCountries(String keyword) {
        List<Country> countries;

        if (keyword != null && !keyword.trim().isEmpty()) {
            countries = countryRepository.searchCountries(keyword.trim());
        } else {
            countries = countryRepository.findAll();
        }

        return countries.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CountryDTO convertToDTO(Country country) {
        CountryDTO dto = new CountryDTO();
        dto.setCountryId(country.getCountryId());
        dto.setCountryName(country.getCountryName());
        dto.setRegionId(country.getRegion().getRegionId());
        dto.setRegionName(country.getRegion().getRegionName());
        dto.setIsActive(country.getIsActive());
        dto.setCreatedAt(country.getCreatedAt());
        dto.setUpdatedAt(country.getUpdatedAt());
        return dto;
    }
}