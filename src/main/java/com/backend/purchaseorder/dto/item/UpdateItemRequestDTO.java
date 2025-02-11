package com.backend.purchaseorder.dto.item;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UpdateItemRequestDTO {
    @Size(max = 500, message = "Name cannot exceed 500 characters")
    private String name;
    
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
    
    @DecimalMin(value = "0.00", message = "Price must be ≥ 0")
    private BigDecimal price;
    
    @DecimalMin(value = "0.00", message = "Cost must be ≥ 0")
    private BigDecimal cost;

    @NotBlank(message = "Updated by cannot be blank")
    private String updatedBy;
}