package com.backend.purchaseorder.dto.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderHeaderDTO {
    private Integer id;
    private LocalDateTime datetime;
    private String description;
    private BigDecimal totalCost;
    private BigDecimal totalPrice;
    private String createdBy;
    private String updatedBy;
    private List<PurchaseOrderDetailDTO> poDetails; // List of PO details
}