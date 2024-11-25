package com.example.receipt_processor.model;

import java.util.List;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Hidden
public class Receipt {
    @NotBlank(message = "Retailer name is required")
    private String retailer;

    @NotNull(message = "Purchase date is required")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Purchase date must be in YYYY-MM-DD format")
    private String purchaseDate;

    @NotNull(message = "Purchase time is required")
    @Pattern(regexp = "\\d{2}:\\d{2}", message = "Purchase time must be in HH:MM format")
    private String purchaseTime;

    @NotNull(message = "Total is required")
    @Pattern(regexp = "\\d+(\\.\\d{1,2})?", message = "Total must be a valid decimal number")
    private String total;

    @NotNull(message = "Items must not be null")
    private List<@NotNull Item> items;
}
