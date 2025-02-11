package com.backend.purchaseorder.controller;

import com.backend.purchaseorder.dto.user.CreateUserRequestDTO;
import com.backend.purchaseorder.dto.user.UpdateUserRequestDTO;
import com.backend.purchaseorder.dto.user.UserResponseDTO;
import com.backend.purchaseorder.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ----------------------- GET ALL USERS -----------------------
    @Test
    void testGetAllUsers() {
        // Mock data
        UserResponseDTO user1 = UserResponseDTO.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("08123456789")
                .createdBy("admin")
                .updatedBy("admin")
                .createdDatetime(LocalDateTime.now())
                .updatedDatetime(LocalDateTime.now())
                .build();

        UserResponseDTO user2 = UserResponseDTO.builder()
                .id(2)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .phone("08123456780")
                .createdBy("user")
                .updatedBy("user")
                .createdDatetime(LocalDateTime.now())
                .updatedDatetime(LocalDateTime.now())
                .build();

        List<UserResponseDTO> mockUsers = Arrays.asList(user1, user2);
        
        // Mock service
        when(userService.getAllUsers()).thenReturn(mockUsers);

        // Test
        ResponseEntity<List<UserResponseDTO>> response = userController.getAllUsers();
        
        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("John", response.getBody().get(0).getFirstName());
    }

    // ----------------------- GET USER BY ID -----------------------
    @Test
    void testGetUserById() {
        // Mock data
        UserResponseDTO mockUser = UserResponseDTO.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("08123456789")
                .createdBy("admin")
                .updatedBy("admin")
                .createdDatetime(LocalDateTime.now())
                .updatedDatetime(LocalDateTime.now())
                .build();
        
        // Mock service
        when(userService.getUserById(1)).thenReturn(mockUser);

        // Test
        ResponseEntity<UserResponseDTO> response = userController.getUserById(1);
        
        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John", response.getBody().getFirstName());
        assertEquals("john@example.com", response.getBody().getEmail());
    }

    // ----------------------- CREATE USER -----------------------
    @Test
    void testCreateUser() {
        // Mock request
        CreateUserRequestDTO request = new CreateUserRequestDTO(
            "John", "Doe", "john@example.com", "08123456789", "admin"
        );
        
        // Mock response
        UserResponseDTO mockResponse = UserResponseDTO.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("08123456789")
                .createdBy("admin")
                .updatedBy("admin")
                .createdDatetime(LocalDateTime.now())
                .updatedDatetime(LocalDateTime.now())
                .build();
        
        // Mock service
        when(userService.createUser(any(CreateUserRequestDTO.class))).thenReturn(mockResponse);

        // Test
        ResponseEntity<UserResponseDTO> response = userController.createUser(request);
        
        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John", response.getBody().getFirstName());
        verify(userService, times(1)).createUser(any(CreateUserRequestDTO.class));
    }

    // ----------------------- UPDATE USER -----------------------
    @Test
    void testUpdateUser() {
        // Mock request menggunakan Builder
        UpdateUserRequestDTO request = UpdateUserRequestDTO.builder()
            .firstName("Jane")
            .lastName("Doe")
            .email("jane@example.com")
            .phone("08123456780")
            .updatedBy("editor")
            .build();
    
        // Mock response
        UserResponseDTO mockResponse = UserResponseDTO.builder()
            .id(1)
            .firstName("Jane")
            .lastName("Doe")
            .email("jane@example.com")
            .phone("08123456780")
            .createdBy("admin")
            .updatedBy("editor")
            .createdDatetime(LocalDateTime.now())
            .updatedDatetime(LocalDateTime.now())
            .build();
    
        // Mock service
        when(userService.updateUser(anyInt(), any(UpdateUserRequestDTO.class))).thenReturn(mockResponse);
    
        // Test
        ResponseEntity<UserResponseDTO> response = userController.updateUser(1, request);
    
        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Jane", response.getBody().getFirstName());
        verify(userService, times(1)).updateUser(anyInt(), any(UpdateUserRequestDTO.class));
    }

    // ----------------------- DELETE USER -----------------------
    @Test
    void testDeleteUser() {
        // Test
        ResponseEntity<Void> response = userController.deleteUser(1);
        
        // Assertions
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(1);
    }
}