package com.backend.purchaseorder.dto.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseOrderHeaderResponseDTO {
    private Integer id;
    private LocalDateTime datetime;
    private String description;
    private BigDecimal totalCost;
    private BigDecimal totalPrice;
    private String createdBy;
    private LocalDateTime createdDatetime;
    private String updatedBy;
    private LocalDateTime updatedDatetime;
    private List<PurchaseOrderDetailResponseDTO> details; 
}
