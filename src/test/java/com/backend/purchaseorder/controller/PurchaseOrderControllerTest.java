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

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void testGetAllPurchaseOrders() {
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

        when(purchaseOrderService.getAllPOs()).thenReturn(mockPOs);

        ResponseEntity<List<PurchaseOrderHeaderDTO>> response = 
            purchaseOrderController.getAllPurchaseOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("admin", response.getBody().get(0).getCreatedBy());
    }

    @Test
    void testGetPurchaseOrderById() {
        PurchaseOrderHeaderDTO mockPO = PurchaseOrderHeaderDTO.builder()
            .id(1)
            .datetime(LocalDateTime.now())
            .totalCost(new BigDecimal("1000000"))
            .totalPrice(new BigDecimal("1200000"))
            .createdBy("admin")
            .build();

        when(purchaseOrderService.getPOById(1)).thenReturn(mockPO);

        ResponseEntity<PurchaseOrderHeaderDTO> response = 
            purchaseOrderController.getPurchaseOrderById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new BigDecimal("1200000"), response.getBody().getTotalPrice());
    }

    @Test
    void testCreatePurchaseOrder() {
        PurchaseOrderHeaderDTO request = PurchaseOrderHeaderDTO.builder()
            .datetime(LocalDateTime.now())
            .createdBy("admin")
            .build();

        PurchaseOrderHeaderDTO mockResponse = PurchaseOrderHeaderDTO.builder()
            .id(1)
            .datetime(LocalDateTime.now())
            .totalCost(new BigDecimal("1000000"))
            .totalPrice(new BigDecimal("1200000"))
            .createdBy("admin")
            .build();

        when(purchaseOrderService.createPO(any(PurchaseOrderHeaderDTO.class)))
            .thenReturn(mockResponse);

        ResponseEntity<PurchaseOrderHeaderDTO> response = 
            purchaseOrderController.createPurchaseOrder(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
        verify(purchaseOrderService, times(1)).createPO(any(PurchaseOrderHeaderDTO.class));
    }

    @Test
    void testUpdatePurchaseOrder() {
        PurchaseOrderHeaderDTO request = PurchaseOrderHeaderDTO.builder()
            .description("Updated PO")
            .updatedBy("editor")
            .build();

        PurchaseOrderHeaderDTO mockResponse = PurchaseOrderHeaderDTO.builder()
            .id(1)
            .description("Updated PO")
            .totalCost(new BigDecimal("1000000"))
            .totalPrice(new BigDecimal("1200000"))
            .createdBy("admin")
            .updatedBy("editor")
            .build();

        when(purchaseOrderService.updatePO(anyInt(), any(PurchaseOrderHeaderDTO.class)))
            .thenReturn(mockResponse);

        ResponseEntity<PurchaseOrderHeaderDTO> response = 
            purchaseOrderController.updatePurchaseOrder(1, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("editor", response.getBody().getUpdatedBy());
        verify(purchaseOrderService, times(1))
            .updatePO(anyInt(), any(PurchaseOrderHeaderDTO.class));
    }

    @Test
    void testDeletePurchaseOrder() {
        ResponseEntity<Void> response = 
            purchaseOrderController.deletePurchaseOrder(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(purchaseOrderService, times(1)).deletePO(1);
    }
}
