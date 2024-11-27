package com.example.receipt_processor.controller;

import com.example.receipt_processor.model.Item;
import com.example.receipt_processor.model.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class ReceiptControllerTest {

    @Mock
    private ReceiptController receiptController;

    @InjectMocks
    private ReceiptControllerTest receiptControllerTest;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void testProcessReceipt_ValidData() throws Exception {
        // Arrange
        Receipt receipt = new Receipt();
        receipt.setRetailer("M&M Corner Market");
        receipt.setPurchaseDate("2022-03-20");
        receipt.setPurchaseTime("14:33");
        receipt.setTotal("9.00");
        receipt.setItems(List.of(
            new Item("Gatorade", "2.25"),
            new Item("Gatorade", "2.25"),
            new Item("Gatorade", "2.25"),
            new Item("Gatorade", "2.25")
        ));

        Map<String, String> expectedResponse = Map.of("id", "12345");

        when(receiptController.processReceipt(any(Receipt.class))).thenReturn(ResponseEntity.ok(expectedResponse));

        // Act
        ResponseEntity<Map<String, String>> response = receiptController.processReceipt(receipt);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(expectedResponse);

        verify(receiptController, times(1)).processReceipt(any(Receipt.class));
    }

    @Test
    void testProcessReceipt_MalformedData() throws Exception {
        // Arrange
        Receipt receipt = new Receipt();
        receipt.setRetailer(""); // Malformed data: missing retailer
        receipt.setPurchaseDate("20-03-2022"); // Invalid date format
        receipt.setPurchaseTime("1433"); // Invalid time format
        receipt.setTotal("ABC"); // Invalid total
        receipt.setItems(List.of(
            new Item("Gatorade", "2.25")
        ));

        when(receiptController.processReceipt(any(Receipt.class)))
                .thenThrow(new IllegalArgumentException("Malformed request body"));

        // Act & Assert
        try {
            receiptController.processReceipt(receipt);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Malformed request body");
        }

        verify(receiptController, times(1)).processReceipt(any(Receipt.class));
    }

    @Test
    void testProcessReceipt_NoData() throws Exception {
        // Arrange
        when(receiptController.processReceipt(null))
                .thenThrow(new IllegalArgumentException("No data provided"));

        // Act & Assert
        try {
            receiptController.processReceipt(null);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("No data provided");
        }

        verify(receiptController, times(1)).processReceipt(null);
    }
}
