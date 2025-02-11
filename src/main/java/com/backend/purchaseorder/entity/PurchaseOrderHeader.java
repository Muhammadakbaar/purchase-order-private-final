package com.backend.purchaseorder.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "po_h")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Created by cannot be blank")
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @CreationTimestamp
    @Column(name = "created_datetime", updatable = false)
    private LocalDateTime createdDatetime;

    @NotNull(message = "Order date cannot be null")
    private LocalDateTime datetime;

    private String description;

    @NotNull
    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @NotNull
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @NotBlank(message = "Updated by cannot be blank")
    @Column(name = "updated_by")
    private String updatedBy;

    @UpdateTimestamp
    @Column(name = "updated_datetime")
    private LocalDateTime updatedDatetime;

    @OneToMany(
        mappedBy = "purchaseOrderHeader",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<PurchaseOrderDetail> details;
}