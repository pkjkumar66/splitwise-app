package com.example.splitwise.controller;

import com.example.splitwise.entity.Expense;
import com.example.splitwise.entity.Split;
import com.example.splitwise.entity.SplitType;
import com.example.splitwise.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseController {
    private List<Expense> expenses = new ArrayList<>();
    private UserExpenseBalanceSheetController balanceSheetController;

    public Expense createExpense(String description, double expenseAmount, User paidByUser, SplitType splitType,
                                 List<Split> splits) {
        updateSplitsBasedOnSplitType(expenseAmount, paidByUser, splitType, splits);

        Expense expense = Expense.builder().build();
        expense.setId(getId());
        expense.setDescription(description);
        expense.setExpenseAmount(expenseAmount);
        expense.setAmountPaidByUser(paidByUser);
        expense.setSplitType(splitType);
        expense.setSplits(splits);

        expenses.add(expense);
        balanceSheetController.updateBalanceSheet(expenseAmount, paidByUser, splits);

        return expense;
    }

    private static void updateSplitsBasedOnSplitType(double expenseAmount, User paidByUser, SplitType splitType, List<Split> splits) {
        if (SplitType.EQUAL.equals(splitType)) {
            double splitAmount = expenseAmount / (1.0) * splits.size();
            splits.forEach(split -> {
                if (Objects.nonNull(paidByUser) && !paidByUser.getId().equals(split.getUser().getId())) {
                    split.setAmountOwe(splitAmount);
                }
            });
        }
    }

    private String getId() {
        return UUID.randomUUID().toString();
    }

}
