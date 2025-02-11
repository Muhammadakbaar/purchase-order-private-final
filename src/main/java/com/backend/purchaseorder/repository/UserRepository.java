package com.backend.purchaseorder.repository;

import com.backend.purchaseorder.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByPhone(String phone);
    boolean existsByPhone(String phone);
}
