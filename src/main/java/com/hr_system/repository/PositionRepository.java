package com.hr_system.repository;

import com.hr_system.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PositionRepository extends JpaRepository<Position, UUID> {
    @Modifying
    @Transactional
    @Query("""
                delete from Position p
                where p.positionId = :id
            """)
    void delete(@Param("id") UUID id);

    Optional<Position> findByPositionName(String positionName);

    List<Position> findAllByIsActiveTrue();

    Optional<Position> findByPositionIdAndIsActiveTrue(UUID positionId);

    boolean existsByPositionNameAndPositionIdNot(String positionName, UUID positionId);
}