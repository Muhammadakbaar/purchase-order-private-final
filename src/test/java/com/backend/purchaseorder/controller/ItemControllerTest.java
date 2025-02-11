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

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void testGetAllItems() {
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

        when(itemService.getAllItems()).thenReturn(mockItems);

        ResponseEntity<List<ItemResponseDTO>> response = itemController.getAllItems();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Laptop", response.getBody().get(0).getName());
    }

    @Test
    void testGetItemById() {
        ItemResponseDTO mockItem = ItemResponseDTO.builder()
                .id(1)
                .name("Laptop")
                .description("High-performance laptop")
                .price(new BigDecimal("15000000"))
                .cost(new BigDecimal("12000000"))
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        when(itemService.getItemById(1)).thenReturn(mockItem);

        ResponseEntity<ItemResponseDTO> response = itemController.getItemById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Laptop", response.getBody().getName());
        assertEquals(new BigDecimal("15000000"), response.getBody().getPrice());
    }

    @Test
    void testCreateItem() {
        CreateItemRequestDTO request = CreateItemRequestDTO.builder()
                .name("Laptop")
                .description("High-performance laptop")
                .price(new BigDecimal("15000000"))
                .cost(new BigDecimal("12000000"))
                .createdBy("admin")
                .build();

        ItemResponseDTO mockResponse = ItemResponseDTO.builder()
                .id(1)
                .name("Laptop")
                .description("High-performance laptop")
                .price(new BigDecimal("15000000"))
                .cost(new BigDecimal("12000000"))
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        when(itemService.createItem(any(CreateItemRequestDTO.class))).thenReturn(mockResponse);

        ResponseEntity<ItemResponseDTO> response = itemController.createItem(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Laptop", response.getBody().getName());
        verify(itemService, times(1)).createItem(any(CreateItemRequestDTO.class));
    }

    @Test
    void testUpdateItem() {
        UpdateItemRequestDTO request = UpdateItemRequestDTO.builder()
                .name("Premium Laptop")
                .description("High-performance laptop with SSD")
                .price(new BigDecimal("16000000"))
                .cost(new BigDecimal("13000000"))
                .updatedBy("editor")
                .build();

        ItemResponseDTO mockResponse = ItemResponseDTO.builder()
                .id(1)
                .name("Premium Laptop")
                .description("High-performance laptop with SSD")
                .price(new BigDecimal("16000000"))
                .cost(new BigDecimal("13000000"))
                .createdBy("admin")
                .updatedBy("editor")
                .build();

        when(itemService.updateItem(anyInt(), any(UpdateItemRequestDTO.class))).thenReturn(mockResponse);

        ResponseEntity<ItemResponseDTO> response = itemController.updateItem(1, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Premium Laptop", response.getBody().getName());
        assertEquals(new BigDecimal("16000000"), response.getBody().getPrice());
        verify(itemService, times(1)).updateItem(anyInt(), any(UpdateItemRequestDTO.class));
    }

    @Test
    void testDeleteItem() {
        ResponseEntity<Void> response = itemController.deleteItem(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(itemService, times(1)).deleteItem(1);
    }
}
