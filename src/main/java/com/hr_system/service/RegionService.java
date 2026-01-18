package com.hr_system.service;

import com.hr_system.dto.RegionDTO;
import com.hr_system.entity.Region;
import com.hr_system.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    @Transactional(readOnly = true)
    public List<RegionDTO> getAllRegions() {
        List<Region> regions = regionRepository.findByIsActiveTrue();
        return regions.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public RegionDTO createRegion(RegionDTO regionDTO) {
        Region region = new Region();
        region.setRegionName(regionDTO.getRegionName());
        region.setDescription(regionDTO.getDescription());

        Region savedRegion = regionRepository.save(region);
        return convertToDTO(savedRegion);
    }

    public RegionDTO updateRegion(UUID regionId, RegionDTO regionDTO) {
        Region region = regionRepository.findByRegionIdAndIsActiveTrue(regionId).orElseThrow(() -> new RuntimeException("Region not found"));

        region.setRegionName(regionDTO.getRegionName());
        region.setDescription(regionDTO.getDescription());

        Region updatedRegion = regionRepository.save(region);
        return convertToDTO(updatedRegion);
    }

    public void deleteRegion(UUID regionId) {
        regionRepository.findByRegionIdAndIsActiveTrue(regionId).orElseThrow(() -> new RuntimeException("Region not found"));
        regionRepository.delete(regionId);
    }

    private RegionDTO convertToDTO(Region region) {
        RegionDTO dto = new RegionDTO();
        dto.setRegionId(region.getRegionId());
        dto.setRegionName(region.getRegionName());
        dto.setDescription(region.getDescription());
        dto.setCreatedAt(region.getCreatedAt());
        dto.setUpdatedAt(region.getUpdatedAt());
        return dto;
    }
}
