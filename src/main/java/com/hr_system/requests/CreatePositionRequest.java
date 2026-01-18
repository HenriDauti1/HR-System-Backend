package com.hr_system.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePositionRequest {
    
    @NotBlank(message = "Position name is required")
    private String positionName;
    
    private Boolean isActive = true;
}