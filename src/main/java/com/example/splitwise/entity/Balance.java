package com.example.splitwise.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Balance {
    private double amountOwe = 0.0;
    private double amountGetBack = 0.0;
}
