package com.backend.purchaseorder.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String createdBy; 
    private String updatedBy; 
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;
}