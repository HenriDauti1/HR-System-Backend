package com.hr_system.repository;

import com.hr_system.entity.view.DepartmentStatisticsView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentStatisticsViewRepository extends JpaRepository<DepartmentStatisticsView, DepartmentStatisticsView.DepartmentStatisticsId> {
}