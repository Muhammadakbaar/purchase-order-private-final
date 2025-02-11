package com.backend.purchaseorder.controller;

import com.backend.purchaseorder.dto.po.PurchaseOrderHeaderDTO;
import com.backend.purchaseorder.service.PurchaseOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class PurchaseOrderControllerTest {

    @Mock
    private PurchaseOrderService purchaseOrderService;

    @InjectMocks
    private PurchaseOrderController purchaseOrderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ----------------------- GET ALL PURCHASE ORDERS -----------------------
    @Test
    void testGetAllPurchaseOrders() {
        // Mock data
        PurchaseOrderHeaderDTO po1 = PurchaseOrderHeaderDTO.builder()
            .id(1)
            .datetime(LocalDateTime.now())
            .totalCost(new BigDecimal("1000000"))
            .totalPrice(new BigDecimal("1200000"))
            .createdBy("admin")
            .build();

        PurchaseOrderHeaderDTO po2 = PurchaseOrderHeaderDTO.builder()
            .id(2)
            .datetime(LocalDateTime.now())
            .totalCost(new BigDecimal("500000"))
            .totalPrice(new BigDecimal("600000"))
            .createdBy("manager")
            .build();

        List<PurchaseOrderHeaderDTO> mockPOs = Arrays.asList(po1, po2);

        // Mock service
        when(purchaseOrderService.getAllPOs()).thenReturn(mockPOs);

        // Test
        ResponseEntity<List<PurchaseOrderHeaderDTO>> response = 
            purchaseOrderController.getAllPurchaseOrders();

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("admin", response.getBody().get(0).getCreatedBy());
    }

    // ----------------------- GET PURCHASE ORDER BY ID -----------------------
    @Test
    void testGetPurchaseOrderById() {
        // Mock data
        PurchaseOrderHeaderDTO mockPO = PurchaseOrderHeaderDTO.builder()
            .id(1)
            .datetime(LocalDateTime.now())
            .totalCost(new BigDecimal("1000000"))
            .totalPrice(new BigDecimal("1200000"))
            .createdBy("admin")
            .build();

        // Mock service
        when(purchaseOrderService.getPOById(1)).thenReturn(mockPO);

        // Test
        ResponseEntity<PurchaseOrderHeaderDTO> response = 
            purchaseOrderController.getPurchaseOrderById(1);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new BigDecimal("1200000"), response.getBody().getTotalPrice());
    }

    // ----------------------- CREATE PURCHASE ORDER -----------------------
    @Test
    void testCreatePurchaseOrder() {
        // Mock request
        PurchaseOrderHeaderDTO request = PurchaseOrderHeaderDTO.builder()
            .datetime(LocalDateTime.now())
            .createdBy("admin")
            .build();

        // Mock response
        PurchaseOrderHeaderDTO mockResponse = PurchaseOrderHeaderDTO.builder()
            .id(1)
            .datetime(LocalDateTime.now())
            .totalCost(new BigDecimal("1000000"))
            .totalPrice(new BigDecimal("1200000"))
            .createdBy("admin")
            .build();

        // Mock service
        when(purchaseOrderService.createPO(any(PurchaseOrderHeaderDTO.class)))
            .thenReturn(mockResponse);

        // Test
        ResponseEntity<PurchaseOrderHeaderDTO> response = 
            purchaseOrderController.createPurchaseOrder(request);

        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
        verify(purchaseOrderService, times(1)).createPO(any(PurchaseOrderHeaderDTO.class));
    }

    // ----------------------- UPDATE PURCHASE ORDER -----------------------
    @Test
    void testUpdatePurchaseOrder() {
        // Mock request
        PurchaseOrderHeaderDTO request = PurchaseOrderHeaderDTO.builder()
            .description("Updated PO")
            .updatedBy("editor")
            .build();

        // Mock response
        PurchaseOrderHeaderDTO mockResponse = PurchaseOrderHeaderDTO.builder()
            .id(1)
            .description("Updated PO")
            .totalCost(new BigDecimal("1000000"))
            .totalPrice(new BigDecimal("1200000"))
            .createdBy("admin")
            .updatedBy("editor")
            .build();

        // Mock service
        when(purchaseOrderService.updatePO(anyInt(), any(PurchaseOrderHeaderDTO.class)))
            .thenReturn(mockResponse);

        // Test
        ResponseEntity<PurchaseOrderHeaderDTO> response = 
            purchaseOrderController.updatePurchaseOrder(1, request);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("editor", response.getBody().getUpdatedBy());
        verify(purchaseOrderService, times(1))
            .updatePO(anyInt(), any(PurchaseOrderHeaderDTO.class));
    }

    // ----------------------- DELETE PURCHASE ORDER -----------------------
    @Test
    void testDeletePurchaseOrder() {
        // Test
        ResponseEntity<Void> response = 
            purchaseOrderController.deletePurchaseOrder(1);

        // Assertions
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(purchaseOrderService, times(1)).deletePO(1);
    }
}