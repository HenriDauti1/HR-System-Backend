package com.hr_system.repository;

import com.hr_system.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<Contract, UUID> {

    List<Contract> findAllByEmployee_IsActiveTrue();

    int countByEndDateIsNull();

    @Query(
        value = """
            SELECT COUNT(*)
            FROM "HR Database".contract c
            WHERE c.start_date >= date_trunc('month', CURRENT_DATE) - INTERVAL '1 year'
              AND c.start_date <  date_trunc('month', CURRENT_DATE)
              AND c.end_date IS NULL
        """,
        nativeQuery = true
    )
    int countLastMonthActiveContracts();
}