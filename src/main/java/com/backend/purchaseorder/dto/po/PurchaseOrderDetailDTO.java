package com.backend.purchaseorder.dto.po;


import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class PurchaseOrderDetailDTO {
    private Integer id;
    private Integer pohId; 
    private Integer itemId;
    private Integer itemQty;
    private BigDecimal itemCost;
    private BigDecimal itemPrice;
}

