package com.hr_system.service;

import com.hr_system.entity.Position;
import com.hr_system.exception.DuplicateResourceException;
import com.hr_system.exception.ResourceNotFoundException;
import com.hr_system.repository.PositionRepository;
import com.hr_system.requests.CreatePositionRequest;
import com.hr_system.requests.UpdatePositionRequest;
import com.hr_system.responses.PositionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    @Transactional(readOnly = true)
    public List<PositionResponse> getAllPositions() {
        List<Position> positions = positionRepository.findAllByIsActiveTrue();
        return positions.stream().map(this::convertToResponse).collect(Collectors.toList());
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
        Position position = positionRepository.findByPositionIdAndIsActiveTrue(id).orElseThrow(() -> new ResourceNotFoundException("Position not found with id: " + id));

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
        positionRepository.findByPositionIdAndIsActiveTrue(id).orElseThrow(() -> new ResourceNotFoundException("Position not found with id: " + id));
        positionRepository.delete(id);
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