package com.backend.purchaseorder.controller;

import com.backend.purchaseorder.dto.po.PurchaseOrderHeaderDTO;
import com.backend.purchaseorder.service.PurchaseOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    // ----------------------- GET ALL PURCHASE ORDERS -----------------------
    @GetMapping
    public ResponseEntity<List<PurchaseOrderHeaderDTO>> getAllPurchaseOrders() {
        return ResponseEntity.ok(purchaseOrderService.getAllPOs());
    }

    // ----------------------- GET PURCHASE ORDER BY ID -----------------------
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderHeaderDTO> getPurchaseOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(purchaseOrderService.getPOById(id));
    }

    // ----------------------- CREATE PURCHASE ORDER -----------------------
    @PostMapping
    public ResponseEntity<PurchaseOrderHeaderDTO> createPurchaseOrder(
        @Valid @RequestBody PurchaseOrderHeaderDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(purchaseOrderService.createPO(request));
    }

    // ----------------------- UPDATE PURCHASE ORDER -----------------------
    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrderHeaderDTO> updatePurchaseOrder(
        @PathVariable Integer id,
        @Valid @RequestBody PurchaseOrderHeaderDTO request
    ) {
        return ResponseEntity.ok(purchaseOrderService.updatePO(id, request));
    }

    // ----------------------- DELETE PURCHASE ORDER -----------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchaseOrder(@PathVariable Integer id) {
        purchaseOrderService.deletePO(id);
        return ResponseEntity.noContent().build();
    }
}