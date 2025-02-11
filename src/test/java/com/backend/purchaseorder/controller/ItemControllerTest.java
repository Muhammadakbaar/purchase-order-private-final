package com.backend.purchaseorder.controller;

import com.backend.purchaseorder.dto.item.CreateItemRequestDTO;
import com.backend.purchaseorder.dto.item.ItemResponseDTO;
import com.backend.purchaseorder.dto.item.UpdateItemRequestDTO;
import com.backend.purchaseorder.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ----------------------- GET ALL ITEMS -----------------------
    @Test
    void testGetAllItems() {
        // Mock data
        ItemResponseDTO item1 = ItemResponseDTO.builder()
                .id(1)
                .name("Laptop")
                .description("High-performance laptop")
                .price(new BigDecimal("15000000"))
                .cost(new BigDecimal("12000000"))
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        ItemResponseDTO item2 = ItemResponseDTO.builder()
                .id(2)
                .name("Printer")
                .description("Wireless printer")
                .price(new BigDecimal("3000000"))
                .cost(new BigDecimal("2500000"))
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        List<ItemResponseDTO> mockItems = Arrays.asList(item1, item2);

        // Mock service
        when(itemService.getAllItems()).thenReturn(mockItems);

        // Test
        ResponseEntity<List<ItemResponseDTO>> response = itemController.getAllItems();

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Laptop", response.getBody().get(0).getName());
    }

    // ----------------------- GET ITEM BY ID -----------------------
    @Test
    void testGetItemById() {
        // Mock data
        ItemResponseDTO mockItem = ItemResponseDTO.builder()
                .id(1)
                .name("Laptop")
                .description("High-performance laptop")
                .price(new BigDecimal("15000000"))
                .cost(new BigDecimal("12000000"))
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        // Mock service
        when(itemService.getItemById(1)).thenReturn(mockItem);

        // Test
        ResponseEntity<ItemResponseDTO> response = itemController.getItemById(1);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Laptop", response.getBody().getName());
        assertEquals(new BigDecimal("15000000"), response.getBody().getPrice());
    }

    // ----------------------- CREATE ITEM -----------------------
    @Test
    void testCreateItem() {
        // Mock request
        CreateItemRequestDTO request = CreateItemRequestDTO.builder()
                .name("Laptop")
                .description("High-performance laptop")
                .price(new BigDecimal("15000000"))
                .cost(new BigDecimal("12000000"))
                .createdBy("admin")
                .build();

        // Mock response
        ItemResponseDTO mockResponse = ItemResponseDTO.builder()
                .id(1)
                .name("Laptop")
                .description("High-performance laptop")
                .price(new BigDecimal("15000000"))
                .cost(new BigDecimal("12000000"))
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        // Mock service
        when(itemService.createItem(any(CreateItemRequestDTO.class))).thenReturn(mockResponse);

        // Test
        ResponseEntity<ItemResponseDTO> response = itemController.createItem(request);

        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Laptop", response.getBody().getName());
        verify(itemService, times(1)).createItem(any(CreateItemRequestDTO.class));
    }

    // ----------------------- UPDATE ITEM -----------------------
    @Test
    void testUpdateItem() {
        // Mock request
        UpdateItemRequestDTO request = UpdateItemRequestDTO.builder()
                .name("Premium Laptop")
                .description("High-performance laptop with SSD")
                .price(new BigDecimal("16000000"))
                .cost(new BigDecimal("13000000"))
                .updatedBy("editor")
                .build();

        // Mock response
        ItemResponseDTO mockResponse = ItemResponseDTO.builder()
                .id(1)
                .name("Premium Laptop")
                .description("High-performance laptop with SSD")
                .price(new BigDecimal("16000000"))
                .cost(new BigDecimal("13000000"))
                .createdBy("admin")
                .updatedBy("editor")
                .build();

        // Mock service
        when(itemService.updateItem(anyInt(), any(UpdateItemRequestDTO.class))).thenReturn(mockResponse);

        // Test
        ResponseEntity<ItemResponseDTO> response = itemController.updateItem(1, request);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Premium Laptop", response.getBody().getName());
        assertEquals(new BigDecimal("16000000"), response.getBody().getPrice());
        verify(itemService, times(1)).updateItem(anyInt(), any(UpdateItemRequestDTO.class));
    }

    // ----------------------- DELETE ITEM -----------------------
    @Test
    void testDeleteItem() {
        // Test
        ResponseEntity<Void> response = itemController.deleteItem(1);

        // Assertions
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(itemService, times(1)).deleteItem(1);
    }
}