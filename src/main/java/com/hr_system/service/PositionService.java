package com.hr_system.service;

import com.hr_system.dto.CreatePositionRequest;
import com.hr_system.dto.PositionResponse;
import com.hr_system.dto.UpdatePositionRequest;
import com.hr_system.entity.Position;
import com.hr_system.exception.DuplicateResourceException;
import com.hr_system.exception.ResourceNotFoundException;
import com.hr_system.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    @Transactional(readOnly = true)
    public List<PositionResponse> getAllPositions(Map<String, String> filterParams) {
        List<Position> positions;

        if (filterParams != null && !filterParams.isEmpty()) {
            String positionId = filterParams.get("positionId");
            String positionName = filterParams.get("positionName");
            String isActive = filterParams.get("isActive");

            if (positionId != null) {
                positions = positionRepository.findByPositionId(UUID.fromString(positionId));
            } else if (positionName != null) {
                positions = positionRepository.findByPositionNameContainingIgnoreCase(positionName);
            } else if (isActive != null) {
                positions = positionRepository.findByIsActive(Boolean.parseBoolean(isActive));
            } else {
                positions = positionRepository.findAll();
            }
        } else {
            positions = positionRepository.findAll();
        }

        return positions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PositionResponse> searchPositions(String keyword) {
        List<Position> positions;

        if (keyword != null && !keyword.trim().isEmpty()) {
            positions = positionRepository.searchPositions(keyword.trim());
        } else {
            positions = positionRepository.findAll();
        }

        return positions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PositionResponse getPositionById(UUID id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with id: " + id));
        return convertToResponse(position);
    }

    @Transactional
    public PositionResponse createPosition(CreatePositionRequest request) {
        if (positionRepository.findByPositionName(request.getPositionName()).isPresent()) {
            throw new DuplicateResourceException("Position with name '" + request.getPositionName() + "' already exists");
        }

        Position position = new Position();
        position.setPositionName(request.getPositionName());
        position.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);

        Position savedPosition = positionRepository.save(position);
        return convertToResponse(savedPosition);
    }

    @Transactional
    public PositionResponse updatePosition(UUID id, UpdatePositionRequest request) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with id: " + id));

        if (positionRepository.existsByPositionNameAndPositionIdNot(request.getPositionName(), id)) {
            throw new DuplicateResourceException("Position with name '" + request.getPositionName() + "' already exists");
        }

        position.setPositionName(request.getPositionName());
        if (request.getIsActive() != null) {
            position.setIsActive(request.getIsActive());
        }

        Position updatedPosition = positionRepository.save(position);
        return convertToResponse(updatedPosition);
    }

    @Transactional
    public void deletePosition(UUID id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with id: " + id));

        position.setIsActive(false);
        positionRepository.save(position);
    }

    private PositionResponse convertToResponse(Position position) {
        PositionResponse response = new PositionResponse();
        response.setPositionId(position.getPositionId());
        response.setPositionName(position.getPositionName());
        response.setIsActive(position.getIsActive());
        response.setCreatedAt(position.getCreatedAt());
        response.setUpdatedAt(position.getUpdatedAt());
        return response;
    }
}