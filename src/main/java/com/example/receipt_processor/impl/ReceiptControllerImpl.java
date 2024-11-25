package com.example.receipt_processor.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.receipt_processor.common.GlobalLogger;
import com.example.receipt_processor.controller.ReceiptController;
import com.example.receipt_processor.model.Receipt;

@RestController
@RequestMapping("/receipts")
@Validated
public class ReceiptControllerImpl implements ReceiptController {

    private final Map<String, Receipt> receiptStorage = new HashMap<>();
    private final GlobalLogger logger;

    public ReceiptControllerImpl(GlobalLogger logger) {
        this.logger = logger;
    }

    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> processReceipt(@RequestBody @Validated Receipt receipt) {
        // Generate ID
        String receiptId = UUID.randomUUID().toString();

        // Store receipt
        receiptStorage.put(receiptId, receipt);

        // Log the receipt processing
        logger.info("Processed receipt with ID: " + receiptId);

        // Return response
        Map<String, String> response = new HashMap<>();
        response.put("id", receiptId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<Map<String, Integer>> getPoints(@PathVariable String id) {
        // Retrieve receipt
        Receipt receipt = receiptStorage.get(id);
        if (receipt == null) {
            logger.warn("Receipt with ID " + id + " not found");
            return ResponseEntity.notFound().build();
        }

        // Calculate points
        int points = calculatePoints(receipt);

        // Log the points calculation
        logger.info("Calculated points for receipt with ID " + id + ": " + points);

        // Return points
        Map<String, Integer> response = new HashMap<>();
        response.put("points", points);
        return ResponseEntity.ok(response);
    }

    private int calculatePoints(Receipt receipt) {
        int points = 0;

        // Rule: One point for every alphanumeric character in the retailer name
        int retailerPoints = receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();
        points += retailerPoints;
        logger.debug("Points from retailer name: " + retailerPoints);

        // Rule: 50 points if the total is a round dollar amount
        if (receipt.getTotal().matches("\\d+\\.00")) {
            points += 50;
            logger.debug("Added 50 points for round dollar amount");
        }

        // Rule: 25 points if the total is a multiple of 0.25
        double total = Double.parseDouble(receipt.getTotal());
        if (total % 0.25 == 0) {
            points += 25;
            logger.debug("Added 25 points for total being a multiple of 0.25");
        }

        // Rule: 5 points for every two items
        int itemPoints = (receipt.getItems().size() / 2) * 5;
        points += itemPoints;
        logger.debug("Points from items: " + itemPoints);

        // Rule: Multiply item description points if length is a multiple of 3
        for (var item : receipt.getItems()) {
            String description = item.getShortDescription().trim();
            if (description.length() % 3 == 0) {
                double price = Double.parseDouble(item.getPrice());
                int descriptionPoints = (int) Math.ceil(price * 0.2);
                points += descriptionPoints;
                logger.debug("Added " + descriptionPoints + " points for item: " + description);
            }
        }

        // Rule: 6 points if purchase date is odd
        String[] dateParts = receipt.getPurchaseDate().split("-");
        int day = Integer.parseInt(dateParts[2]);
        if (day % 2 != 0) {
            points += 6;
            logger.debug("Added 6 points for odd purchase date");
        }

        // Rule: 10 points if purchase time is between 2:00pm and 4:00pm
        String[] timeParts = receipt.getPurchaseTime().split(":");
        int hour = Integer.parseInt(timeParts[0]);
        if (hour == 14) {
            points += 10;
            logger.debug("Added 10 points for purchase time between 2:00pm and 4:00pm");
        }

        return points;
    }
}
