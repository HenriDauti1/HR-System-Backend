package com.hr_system.repository;

import com.hr_system.entity.view.ActiveEmployeeView;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ActiveEmployeeViewRepository extends JpaRepository<ActiveEmployeeView, UUID> {
}