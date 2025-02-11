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

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void testGetAllUsers() {
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
        
        when(userService.getAllUsers()).thenReturn(mockUsers);

        ResponseEntity<List<UserResponseDTO>> response = userController.getAllUsers();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("John", response.getBody().get(0).getFirstName());
    }

    @Test
    void testGetUserById() {
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
        
        when(userService.getUserById(1)).thenReturn(mockUser);

        ResponseEntity<UserResponseDTO> response = userController.getUserById(1);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John", response.getBody().getFirstName());
        assertEquals("john@example.com", response.getBody().getEmail());
    }

    @Test
    void testCreateUser() {
        CreateUserRequestDTO request = new CreateUserRequestDTO(
            "John", "Doe", "john@example.com", "08123456789", "admin"
        );
        
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
        
        when(userService.createUser(any(CreateUserRequestDTO.class))).thenReturn(mockResponse);

        ResponseEntity<UserResponseDTO> response = userController.createUser(request);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John", response.getBody().getFirstName());
        verify(userService, times(1)).createUser(any(CreateUserRequestDTO.class));
    }

    @Test
    void testUpdateUser() {
        UpdateUserRequestDTO request = UpdateUserRequestDTO.builder()
            .firstName("Jane")
            .lastName("Doe")
            .email("jane@example.com")
            .phone("08123456780")
            .updatedBy("editor")
            .build();
    
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
    
        when(userService.updateUser(anyInt(), any(UpdateUserRequestDTO.class))).thenReturn(mockResponse);
    
        ResponseEntity<UserResponseDTO> response = userController.updateUser(1, request);
    
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Jane", response.getBody().getFirstName());
        verify(userService, times(1)).updateUser(anyInt(), any(UpdateUserRequestDTO.class));
    }

    @Test
    void testDeleteUser() {
        ResponseEntity<Void> response = userController.deleteUser(1);
        
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(1);
    }
}
