package com.example.receipt_processor.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.receipt_processor.model.Receipt;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Validated
public interface ReceiptController {

    //Process a receipt and generate an ID for it.

    @PostMapping("/process")
    @Operation(
        summary = "Process a receipt",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Receipt to process",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    example = """
                    {
                    "retailer": "M&M Corner Market",
                    "purchaseDate": "2022-03-20",
                    "purchaseTime": "14:33",
                    "items": [
                        {
                        "shortDescription": "Gatorade",
                        "price": "2.25"
                        },
                        {
                        "shortDescription": "Gatorade",
                        "price": "2.25"
                        },
                        {
                        "shortDescription": "Gatorade",
                        "price": "2.25"
                        },
                        {
                        "shortDescription": "Gatorade",
                        "price": "2.25"
                        }
                    ],
                    "total": "9.00"
                    }
                    """
                )
            )
        )
    )
    ResponseEntity<Map<String, String>> processReceipt(
        @RequestBody @Validated

        Receipt receipt
    );

    //Get the points for a receipt by ID.
    @Operation(summary = "Retrieve points for a receipt")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Points retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(example = "{\"points\":109}")
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Receipt not found",
            content = @Content(mediaType = "application/json")
        )
    })
    @GetMapping("/{id}/points")
    ResponseEntity<Map<String, Integer>> getPoints(@PathVariable String id);
}
