package com.backend.purchaseorder.dto.item;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ItemResponseDTO {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal cost;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;
}