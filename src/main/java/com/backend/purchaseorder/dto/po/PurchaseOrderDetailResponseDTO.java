package com.backend.purchaseorder.dto.po;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseOrderDetailResponseDTO {
    private Integer itemId;
    private Integer itemQty;
    private BigDecimal itemCost;
    private BigDecimal itemPrice;
}
