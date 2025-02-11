package com.backend.purchaseorder.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequestDTO {
    @Size(max = 500, message = "First name cannot exceed 500 characters")
    private String firstName;

    @Size(max = 500, message = "Last name cannot exceed 500 characters")
    private String lastName;

    @Email(message = "Invalid email format")
    private String email;

    @Size(max = 12, message = "Phone number cannot exceed 12 characters")
    private String phone;

    @NotBlank(message = "Updated by cannot be blank")
    private String updatedBy;
}