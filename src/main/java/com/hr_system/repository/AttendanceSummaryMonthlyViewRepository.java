package com.hr_system.repository;

import com.hr_system.entity.view.AttendanceSummaryMonthlyView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface AttendanceSummaryMonthlyViewRepository extends JpaRepository<AttendanceSummaryMonthlyView, Serializable> {
}