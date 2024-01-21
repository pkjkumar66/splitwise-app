package com.example.splitwise.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserExpenseBalanceSheet {
    private double amountYouOwe = 0.0;
    private double amountYouGetBack = 0.0;
    private double totalExpense = 0.0;
    private double totalAmountPaid = 0.0;
    private Map<String, Balance> userVsBalance = new HashMap<>();
}
