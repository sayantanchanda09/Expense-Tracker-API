package com.sayantan.expense_tracker.repository;

import com.sayantan.expense_tracker.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Page<Expense> findByCategory(String category, Pageable pageable);
    Page<Expense> findByOwnerUsername(String username, Pageable pageable);
    Page<Expense> findByOwnerUsernameAndCategory(String username, String category, Pageable pageable);
}
