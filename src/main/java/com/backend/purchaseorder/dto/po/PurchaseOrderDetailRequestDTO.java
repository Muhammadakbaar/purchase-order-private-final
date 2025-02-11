package com.backend.purchaseorder.dto.po;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;


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

