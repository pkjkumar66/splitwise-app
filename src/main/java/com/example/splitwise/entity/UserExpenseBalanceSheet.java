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
    private double amountYouOwe;
    private double amountYouGetBack;
    private double totalExpense;
    private double totalAmountPaid;
    private Map<String, Balance> userVsBalance = new HashMap<>();
}
