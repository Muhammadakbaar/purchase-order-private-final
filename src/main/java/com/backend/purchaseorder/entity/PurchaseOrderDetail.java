package com.backend.purchaseorder.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "po_d")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Header cannot be null")
    @ManyToOne
    @JoinColumn(name = "poh_id", nullable = false)
    private PurchaseOrderHeader purchaseOrderHeader;

    @NotNull(message = "Item ID cannot be null")
    @Column(name = "item_id")
    private Integer itemId;

    @Positive(message = "Quantity must be positive")
    @Column(name = "item_qty")
    private Integer itemQty;

    @DecimalMin(value = "0.00", message = "Cost must be ≥ 0")
    @Column(name = "item_cost")
    private BigDecimal itemCost;

    @DecimalMin(value = "0.00", message = "Price must be ≥ 0")
    @Column(name = "item_price")
    private BigDecimal itemPrice;

    @CreationTimestamp
    @Column(name = "created_datetime", updatable = false)
    private LocalDateTime createdDatetime;
}