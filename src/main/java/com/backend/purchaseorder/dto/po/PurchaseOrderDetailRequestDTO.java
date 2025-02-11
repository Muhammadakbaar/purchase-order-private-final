package com.backend.purchaseorder.dto.po;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PurchaseOrderDetailRequestDTO {
    @NotNull(message = "Item ID cannot be null")
    private Integer itemId;

    @Positive(message = "Quantity must be positive")
    private Integer itemQty;

    @DecimalMin(value = "0.00", message = "Cost must be ≥ 0")
    private BigDecimal itemCost;

    @DecimalMin(value = "0.00", message = "Price must be ≥ 0")
    private BigDecimal itemPrice;
}

