package com.backend.purchaseorder.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 500, message = "Name cannot exceed 500 characters")
    @Column(unique = true)
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @DecimalMin(value = "0.00", message = "Price must be greater than or equal to 0")
    @Column(precision = 10, scale = 2) // Contoh: 99999999.99
    private BigDecimal price;

    @DecimalMin(value = "0.00", message = "Cost must be greater than or equal to 0")
    @Column(precision = 10, scale = 2)
    private BigDecimal cost;

    @NotBlank(message = "Created by cannot be blank")
    @Size(max = 100, message = "Created by cannot exceed 100 characters")
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Size(max = 100, message = "Updated by cannot exceed 100 characters")
    @Column(name = "updated_by")
    private String updatedBy;

    @CreationTimestamp
    @Column(name = "created_datetime", updatable = false)
    private LocalDateTime createdDatetime;

    @UpdateTimestamp
    @Column(name = "updated_datetime")
    private LocalDateTime updatedDatetime;
}