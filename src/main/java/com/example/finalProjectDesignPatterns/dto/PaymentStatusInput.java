package com.example.finalProjectDesignPatterns.dto;

import com.example.finalProjectDesignPatterns.Enum.PaymentStatus;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
public class PaymentStatusInput {
    private PaymentStatus paymentStatus;
}
