package com.backend.purchaseorder.service;

import com.backend.purchaseorder.dto.user.CreateUserRequestDTO;
import com.backend.purchaseorder.dto.user.UpdateUserRequestDTO;
import com.backend.purchaseorder.dto.user.UserResponseDTO;
import com.backend.purchaseorder.entity.User;
import com.backend.purchaseorder.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // ----------------------- GET ALL USERS -----------------------
    public List<UserResponseDTO> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    // ----------------------- GET USER BY ID -----------------------
    public UserResponseDTO getUserById(Integer id) {
        log.info("Fetching user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return convertToResponseDTO(user);
    }

    // ----------------------- CREATE USER -----------------------
    public UserResponseDTO createUser(CreateUserRequestDTO request) {
        // Validasi duplikasi email/phone
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
    
        User newUser = User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .phone(request.getPhone())
            .createdBy(request.getCreatedBy()) // Diambil dari DTO
            .updatedBy(request.getCreatedBy()) // Sama dengan createdBy
            .build();
    
        User savedUser = userRepository.save(newUser);
        return convertToResponseDTO(savedUser);
    }

    // ----------------------- UPDATE USER -----------------------
    public UserResponseDTO updateUser(Integer id, UpdateUserRequestDTO request) {
        User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    
        // Update field yang diisi (jika tidak null)
        Optional.ofNullable(request.getFirstName()).ifPresent(existingUser::setFirstName);
        Optional.ofNullable(request.getLastName()).ifPresent(existingUser::setLastName);
        
        // Validasi email jika diubah
        if (request.getEmail() != null && !request.getEmail().equals(existingUser.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already exists: " + request.getEmail());
            }
            existingUser.setEmail(request.getEmail());
        }
    
        // Validasi nomor telepon jika diubah
        if (request.getPhone() != null && !request.getPhone().equals(existingUser.getPhone())) {
            if (userRepository.existsByPhone(request.getPhone())) {
                throw new RuntimeException("Phone already exists: " + request.getPhone());
            }
            existingUser.setPhone(request.getPhone());
        }
    
        // Update audit field
        existingUser.setUpdatedBy(request.getUpdatedBy());
        existingUser.setUpdatedDatetime(LocalDateTime.now());
    
        User updatedUser = userRepository.save(existingUser);
        return convertToResponseDTO(updatedUser);
    }

    // ----------------------- DELETE USER -----------------------
    public void deleteUser(Integer id) {
        log.info("Deleting user with id: {}", id);
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    // ----------------------- CONVERSION METHODS -----------------------
    private UserResponseDTO convertToResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .createdBy(user.getCreatedBy())
                .updatedBy(user.getUpdatedBy())
                .createdDatetime(user.getCreatedDatetime())
                .updatedDatetime(user.getUpdatedDatetime())
                .build();
    }
}