package com.sayantan.expense_tracker.controller;

import com.sayantan.expense_tracker.dto.ExpenseResponse;
import com.sayantan.expense_tracker.model.Expense;
import com.sayantan.expense_tracker.service.ExpenseService;
import com.sayantan.expense_tracker.dto.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ExpenseResponse createExpense(@Valid @RequestBody Expense expense, Authentication authentication) {
        Expense saved = expenseService.addExpense(expense, authentication.getName());
        return new ExpenseResponse(saved);
    }

    @GetMapping
    public PageResponse<ExpenseResponse> getAllExpenses(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            Authentication authentication) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        String username = authentication.getName();

        Page<Expense> result = (category != null)
                ? expenseService.getExpensesByCategory(username, category, pageable)
                : expenseService.getAllExpensesPaged(username, pageable);

        Page<ExpenseResponse> mapped = result.map(ExpenseResponse::new);
        return new PageResponse<>(mapped);
    }

    @GetMapping("/{id}")
    public ExpenseResponse getExpenseById(@PathVariable Long id, Authentication authentication){
        Expense expense = expenseService.getExpenseById(id, authentication.getName());
        return new ExpenseResponse(expense);
    }

    @PutMapping("/{id}")
    public ExpenseResponse updateExpense(@PathVariable Long id, @RequestBody Expense expense, Authentication authentication){
        Expense updated = expenseService.updateExpense(id, expense, authentication.getName());
        return new ExpenseResponse(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id, Authentication authentication){
        expenseService.deleteExpense(id, authentication.getName());
    }
}