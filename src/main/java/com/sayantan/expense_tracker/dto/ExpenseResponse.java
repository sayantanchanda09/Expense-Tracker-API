package com.sayantan.expense_tracker.dto;

import com.sayantan.expense_tracker.model.Expense;

public class ExpenseResponse {
    private Long id;
    private String title;
    private Double amount;
    private String category;
    private String ownerUsername;

    public ExpenseResponse(Expense expense) {
        this.id = expense.getId();
        this.title = expense.getTitle();
        this.amount = expense.getAmount();
        this.category = expense.getCategory();
        this.ownerUsername =expense.getOwner().getUsername();
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public String getCategory() {
        return category;
    }

    public Double getAmount() {
        return amount;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }
}
