package com.backend.purchaseorder.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDTO {
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Phone cannot be blank")
    private String phone;

    @NotBlank(message = "Created by cannot be blank")
    private String createdBy; 
}