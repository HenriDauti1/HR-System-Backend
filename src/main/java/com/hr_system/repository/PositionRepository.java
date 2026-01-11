package com.hr_system.repository;

import com.hr_system.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PositionRepository extends JpaRepository<Position, UUID> {

    Optional<Position> findByPositionName(String positionName);

    boolean existsByPositionNameAndPositionIdNot(String positionName, UUID positionId);

    List<Position> findByPositionId(UUID positionId);

    List<Position> findByPositionNameContainingIgnoreCase(String positionName);

    List<Position> findByIsActive(Boolean isActive);

    @Query("SELECT p FROM Position p WHERE " +
            "LOWER(p.positionName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Position> searchPositions(@Param("keyword") String keyword);
}