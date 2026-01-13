package com.hr_system.repository;

import com.hr_system.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<Contract, UUID> {

    List<Contract> findByContractId(UUID contractId);

    List<Contract> findByEmployee_EmployeeId(UUID employeeId);

    List<Contract> findByContractType(String contractType);

    @Query("SELECT c FROM Contract c WHERE " +
            "LOWER(c.contractType) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.employee.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.employee.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Contract> searchContracts(@Param("keyword") String keyword);
}