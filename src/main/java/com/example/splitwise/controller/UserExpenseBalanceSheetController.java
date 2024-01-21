package com.example.splitwise.controller;

import com.example.splitwise.entity.Balance;
import com.example.splitwise.entity.Split;
import com.example.splitwise.entity.User;
import com.example.splitwise.entity.UserExpenseBalanceSheet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserExpenseBalanceSheetController {
    public void updateBalanceSheet(double expenseAmount, User paidByUser, List<Split> splits) {
        splits.forEach(split -> {
            if (Objects.nonNull(paidByUser) && paidByUser.getId().equals(split.getUser().getId())) {
                double splitAmount = expenseAmount / (1.0 * splits.size());
                UserExpenseBalanceSheet paidByUserBalanceSheet = paidByUser.getBalanceSheet();
                double totalExpense = paidByUserBalanceSheet.getTotalExpense();
                double totalAmountPaid = paidByUserBalanceSheet.getTotalAmountPaid();
                totalExpense += splitAmount;
                totalAmountPaid += expenseAmount;

                paidByUserBalanceSheet.setTotalExpense(totalExpense);
                paidByUserBalanceSheet.setTotalAmountPaid(totalAmountPaid);
                paidByUser.setBalanceSheet(paidByUserBalanceSheet);
            } else {
                UserExpenseBalanceSheet paidByUserBalanceSheet = paidByUser.getBalanceSheet();
                User getBackFromUser = split.getUser();

                double amountYouGetBack = paidByUserBalanceSheet.getAmountYouGetBack();
                amountYouGetBack += split.getAmountOwe();

                Map<String, Balance> userVsBalanceOfPaidUser = paidByUserBalanceSheet.getUserVsBalance();
                Balance balance = userVsBalanceOfPaidUser.getOrDefault(getBackFromUser.getId(), Balance.builder().build());
                balance.setAmountGetBack(amountYouGetBack);
                userVsBalanceOfPaidUser.replace(getBackFromUser.getId(), balance);

                paidByUserBalanceSheet.setAmountYouGetBack(amountYouGetBack);
                paidByUserBalanceSheet.setUserVsBalance(userVsBalanceOfPaidUser);
                paidByUser.setBalanceSheet(paidByUserBalanceSheet);

                UserExpenseBalanceSheet getBackFromUserBalanceSheet = getBackFromUser.getBalanceSheet();
                double amountYouOwe = getBackFromUserBalanceSheet.getAmountYouOwe();
                amountYouOwe += split.getAmountOwe();

                double totalExpense = getBackFromUserBalanceSheet.getTotalExpense();
                totalExpense += split.getAmountOwe();

                Map<String, Balance> userVsBalanceOfOweUser = getBackFromUserBalanceSheet.getUserVsBalance();
                Balance balance1 = userVsBalanceOfOweUser.getOrDefault(paidByUser.getId(), Balance.builder().build());
                balance1.setAmountOwe(split.getAmountOwe());
                userVsBalanceOfOweUser.replace(paidByUser.getId(), balance1);

                getBackFromUserBalanceSheet.setAmountYouOwe(amountYouOwe);
                getBackFromUserBalanceSheet.setTotalExpense(totalExpense);
                getBackFromUserBalanceSheet.setUserVsBalance(userVsBalanceOfOweUser);
                getBackFromUser.setBalanceSheet(getBackFromUserBalanceSheet);
            }
        });
    }
}
