package com.backend.purchaseorder.repository;

import com.backend.purchaseorder.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    // Cari user berdasarkan email (untuk login/validasi)
    Optional<User> findByEmail(String email);

    // Cek apakah email sudah terdaftar (untuk validasi duplikasi)
    boolean existsByEmail(String email);

    // Cari user berdasarkan nomor telepon
    Optional<User> findByPhone(String phone);

    // Cek apakah nomor telepon sudah terdaftar
    boolean existsByPhone(String phone);
}