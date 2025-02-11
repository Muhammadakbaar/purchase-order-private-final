package com.backend.purchaseorder.controller;

import com.backend.purchaseorder.dto.user.CreateUserRequestDTO;
import com.backend.purchaseorder.dto.user.UpdateUserRequestDTO;
import com.backend.purchaseorder.dto.user.UserResponseDTO;
import com.backend.purchaseorder.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ----------------------- GET ALL USERS -----------------------
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        log.info("Fetching all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // ----------------------- GET USER BY ID -----------------------
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Integer id) {
        log.info("Fetching user by id: {}", id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // ----------------------- CREATE USER -----------------------
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
        @Valid @RequestBody CreateUserRequestDTO request
    ) {
        log.info("Creating user with email: {}", request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(userService.createUser(request));
    }

    // ----------------------- UPDATE USER -----------------------
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
        @PathVariable Integer id, // ID diambil dari URL
        @Valid @RequestBody UpdateUserRequestDTO request
    ) {
        log.info("Updating user with id: {}", id);
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    // ----------------------- DELETE USER -----------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        log.info("Deleting user with id: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}