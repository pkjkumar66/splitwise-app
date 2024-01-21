package com.example.splitwise.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String name;
    private String emailId;
    private String mobileNum;
    private String password;
    private UserExpenseBalanceSheet balanceSheet;
}
