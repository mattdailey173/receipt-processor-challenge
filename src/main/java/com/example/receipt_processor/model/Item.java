package com.example.receipt_processor.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Hidden
public class Item {
    @NotBlank(message = "Item description is required")
    private String shortDescription;

    @NotNull(message = "Item price is required")
    @Pattern(regexp = "\\d+(\\.\\d{1,2})?", message = "Item price must be a valid decimal number")
    private String price;

    public Item(String shortDescription, String price) {
        this.shortDescription = shortDescription;
        this.price = price;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    //removed Lombok for better IDE and OS integration
    // Getter and Setter for price
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
