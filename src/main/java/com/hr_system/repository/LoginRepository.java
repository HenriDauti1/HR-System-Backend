package com.hr_system.repository;

import com.hr_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoginRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
}
