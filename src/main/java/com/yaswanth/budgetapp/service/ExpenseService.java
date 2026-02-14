package com.yaswanth.budgetapp.service;
import org.springframework.data.domain.Page;
import com.yaswanth.budgetapp.dto.ExpenseRequest;
import com.yaswanth.budgetapp.dto.ExpenseResponse;
import com.yaswanth.budgetapp.model.Expense;
import java.util.List;

public interface ExpenseService {

    ExpenseResponse addExpense(ExpenseRequest request);

    ExpenseResponse getExpenseById(Long id);

    ExpenseResponse updateExpense(Long id, ExpenseRequest request);

    void deleteExpense(Long id);

    Page<ExpenseResponse> getAllExpenses(int page,
                                         int size,
                                         String sortBy,
                                         String direction);


}
