package com.example.receipt_processor.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Hidden
public class Item {
    @NotBlank(message = "Item description is required")
    private String shortDescription;
    
    @NotNull(message = "Item price is required")
    @Pattern(regexp = "\\d+(\\.\\d{1,2})?", message = "Item price must be a valid decimal number")
    private String price;
}
