package com.hr_system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePositionRequest {
    
    @NotBlank(message = "Position name is required")
    private String positionName;
    
    private Boolean isActive;
}