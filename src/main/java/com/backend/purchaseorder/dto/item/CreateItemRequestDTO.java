package com.backend.purchaseorder.dto.item;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateItemRequestDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    
    private String description;
    
    @DecimalMin(value = "0.00", message = "Price must be ≥ 0")
    private BigDecimal price;
    
    @DecimalMin(value = "0.00", message = "Cost must be ≥ 0")
    private BigDecimal cost;

    @NotBlank(message = "Created by cannot be blank")
    private String createdBy;

    private String updatedBy;
}