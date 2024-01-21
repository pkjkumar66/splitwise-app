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

                paidByUserBalanceSheet.setTotalExpense(paidByUserBalanceSheet.getTotalExpense() + splitAmount);
                paidByUserBalanceSheet.setTotalAmountPaid(paidByUserBalanceSheet.getTotalAmountPaid() + expenseAmount);
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
                Map<String, Balance> userVsBalanceOfOweUser = getBackFromUserBalanceSheet.getUserVsBalance();
                Balance balance1 = userVsBalanceOfOweUser.computeIfAbsent(paidByUser.getId(), id -> Balance.builder().build());
                balance1.setAmountOwe(split.getAmountOwe());

                getBackFromUserBalanceSheet.setAmountYouOwe(getBackFromUserBalanceSheet.getAmountYouOwe() + split.getAmountOwe());
                getBackFromUserBalanceSheet.setTotalExpense(getBackFromUserBalanceSheet.getTotalExpense() + split.getAmountOwe());
                getBackFromUserBalanceSheet.setUserVsBalance(userVsBalanceOfOweUser);
                getBackFromUser.setBalanceSheet(getBackFromUserBalanceSheet);
            }
        });
    }

    public void showBalanceSheetOfUser(User user) {

        System.out.println("---------------------------------------");

        System.out.println("Balance sheet of user : " + user.getId());

        UserExpenseBalanceSheet userExpenseBalanceSheet = user.getBalanceSheet();

        System.out.println("TotalExpense: " + userExpenseBalanceSheet.getTotalExpense());
        System.out.println("TotalYouGetBack: " + userExpenseBalanceSheet.getAmountYouGetBack());
        System.out.println("TotalYouOwe: " + userExpenseBalanceSheet.getAmountYouOwe());
        System.out.println("TotalAmountPaid: " + userExpenseBalanceSheet.getTotalAmountPaid());

        for (Map.Entry<String, Balance> entry : userExpenseBalanceSheet.getUserVsBalance().entrySet()) {
            String userID = entry.getKey();
            Balance balance = entry.getValue();

            System.out.println("userID:" + userID + " YouGetBack:" + balance.getAmountGetBack() + " YouOwe:" + balance.getAmountOwe());
        }
        System.out.println("---------------------------------------");

    }
}
