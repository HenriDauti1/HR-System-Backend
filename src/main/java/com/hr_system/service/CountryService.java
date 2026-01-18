package com.hr_system.service;

import com.hr_system.dto.CountryDTO;
import com.hr_system.entity.Country;
import com.hr_system.entity.Region;
import com.hr_system.exception.ResourceNotFoundException;
import com.hr_system.repository.CountryRepository;
import com.hr_system.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;
    private final RegionRepository regionRepository;

    @Transactional(readOnly = true)
    public List<CountryDTO> getAllCountries() {
        List<Country> countries = countryRepository.findByIsActiveTrue();
        return countries.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public CountryDTO createCountry(CountryDTO countryDTO) {
        countryRepository.findByCountryName(countryDTO.getCountryName()).ifPresent(c -> {
            throw new IllegalArgumentException("Country with name already exists");
        });

        Region region = regionRepository.findByRegionIdAndIsActiveTrue(countryDTO.getRegionId()).orElseThrow(() -> new ResourceNotFoundException("Region not found with id: " + countryDTO.getRegionId()));

        Country country = new Country();
        country.setCountryName(countryDTO.getCountryName());
        country.setRegion(region);
        country.setIsActive(countryDTO.getIsActive() == null || countryDTO.getIsActive());

        Country saved = countryRepository.save(country);
        return convertToDTO(saved);
    }

    @Transactional
    public CountryDTO updateCountry(UUID id, CountryDTO countryDTO) {
        Country existing = countryRepository.findByCountryIdAndIsActiveTrue(id).orElseThrow(() -> new ResourceNotFoundException("Country not found with id: " + id));

        if (countryDTO.getCountryName() != null && !countryDTO.getCountryName().equals(existing.getCountryName())) {
            countryRepository.findByCountryName(countryDTO.getCountryName()).ifPresent(c -> {
                if (!c.getCountryId().equals(id))
                    throw new IllegalArgumentException("Country with name already exists");
            });
            existing.setCountryName(countryDTO.getCountryName());
        }

        if (countryDTO.getRegionId() != null && (existing.getRegion() == null || !countryDTO.getRegionId().equals(existing.getRegion().getRegionId()))) {
            Region region = regionRepository.findByRegionIdAndIsActiveTrue(countryDTO.getRegionId()).orElseThrow(() -> new ResourceNotFoundException("Region not found with id: " + countryDTO.getRegionId()));
            existing.setRegion(region);
        }

        if (countryDTO.getIsActive() != null) {
            existing.setIsActive(countryDTO.getIsActive());
        }

        Country updated = countryRepository.save(existing);
        return convertToDTO(updated);
    }

    @Transactional
    public void deleteCountry(UUID id) {
        countryRepository.findByCountryIdAndIsActiveTrue(id).orElseThrow(() -> new ResourceNotFoundException("Country not found with id: " + id));
        countryRepository.delete(id);
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
