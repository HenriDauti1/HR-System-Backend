package com.hr_system.service;

import com.hr_system.dto.RegionDTO;
import com.hr_system.entity.Region;
import com.hr_system.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    @Transactional(readOnly = true)
    public List<RegionDTO> getAllRegions(Map<String, String> filterParams) {
        List<Region> regions;

        if (filterParams != null && !filterParams.isEmpty()) {
            String regionId = filterParams.get("regionId");
            String regionName = filterParams.get("regionName");
            String description = filterParams.get("description");
            String isActive = filterParams.get("isActive");

            if (regionId != null) {
                regions = regionRepository.findByRegionId(UUID.fromString(regionId));
            } else if (regionName != null) {
                regions = regionRepository.findByRegionName(regionName);
            } else if (description != null) {
                regions = regionRepository.findByDescriptionContainingIgnoreCase(description);
            } else if (isActive != null) {
                regions = regionRepository.findByIsActive(Boolean.parseBoolean(isActive));
            } else {
                regions = regionRepository.findAll();
            }
        } else {
            regions = regionRepository.findAll();
        }

        return regions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RegionDTO> searchRegions(String keyword) {
        List<Region> regions;

        if (keyword != null && !keyword.trim().isEmpty()) {
            regions = regionRepository.searchRegions(keyword.trim());
        } else {
            regions = regionRepository.findAll();
        }

        return regions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private RegionDTO convertToDTO(Region region) {
        RegionDTO dto = new RegionDTO();
        dto.setRegionId(region.getRegionId());
        dto.setRegionName(region.getRegionName());
        dto.setDescription(region.getDescription());
        dto.setIsActive(region.getIsActive());
        dto.setCreatedAt(region.getCreatedAt());
        dto.setUpdatedAt(region.getUpdatedAt());
        return dto;
    }
}
