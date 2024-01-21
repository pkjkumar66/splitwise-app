package com.example.splitwise.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    private String id;
    private String description;
    private double expenseAmount = 0.0;
    private User amountPaidByUser;
    private SplitType splitType;
    private List<Split> splits;
}
