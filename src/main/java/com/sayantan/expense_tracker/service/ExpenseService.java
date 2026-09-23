package com.sayantan.expense_tracker.service;

import com.sayantan.expense_tracker.exception.ExpenseNotFoundException;
import com.sayantan.expense_tracker.model.Expense;
import com.sayantan.expense_tracker.model.User;
import com.sayantan.expense_tracker.repository.ExpenseRepository;
import com.sayantan.expense_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    public Expense addExpense(Expense expense, String username) {
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        expense.setOwner(owner);
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long id, String username){
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException(id));
        if (!expense.getOwner().getUsername().equals(username)) {
            throw new RuntimeException("Access denied");
        }
        return expense;
    }

    public Expense updateExpense(Long id, Expense updatedExpense, String username) {
        Expense expense = getExpenseById(id, username);
        expense.setTitle(updatedExpense.getTitle());
        expense.setAmount(updatedExpense.getAmount());
        expense.setCategory(updatedExpense.getCategory());
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id, String username){
        Expense expense = getExpenseById(id, username);
        expenseRepository.delete(expense);
    }

    public Page<Expense> getAllExpensesPaged(String username, Pageable pageable) {
        return expenseRepository.findByOwnerUsername(username, pageable);
    }

    public Page<Expense> getExpensesByCategory(String username, String category, Pageable pageable) {
        return expenseRepository.findByOwnerUsernameAndCategory(username, category, pageable);
    }
}