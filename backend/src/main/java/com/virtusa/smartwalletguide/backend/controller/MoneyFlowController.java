package com.virtusa.smartwalletguide.backend.controller;

import com.virtusa.smartwalletguide.backend.entitiy.Expense;
import com.virtusa.smartwalletguide.backend.entitiy.Income;
import com.virtusa.smartwalletguide.backend.entitiy.Saving;
import com.virtusa.smartwalletguide.backend.repository.ExpenseRepository;
import com.virtusa.smartwalletguide.backend.repository.IncomeRepository;
import com.virtusa.smartwalletguide.backend.repository.SavingRepository;
import com.virtusa.smartwalletguide.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/money")
public class MoneyFlowController {
    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private SavingRepository savingRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/income")
    public ResponseEntity<Income> incomeSave(@RequestBody Income income, Authentication authentication){
        income.setUser(userRepository.findByEmail(authentication.getName()));
        System.out.println("add income");
        System.out.println(income.toString());
        return ResponseEntity.ok(incomeRepository.save(income));
    }

    @GetMapping("/income")
    public ResponseEntity<List<Income>> getIncome(@PathVariable int userId){
        return ResponseEntity.ok(incomeRepository.findByUser(userRepository.findById(userId)));
    }

    @PostMapping("/saving")
    public ResponseEntity<Saving> savingSave(@RequestBody Saving saving){
        return ResponseEntity.ok(savingRepository.save(saving));
    }

    @GetMapping("/saving")
    public ResponseEntity<List<Saving>> getSaving(@PathVariable int userId){
        return ResponseEntity.ok(savingRepository.findByUser(userRepository.findById(userId)));
    }

    @PostMapping("/expense")
    public ResponseEntity<Expense> expenseSave(@RequestBody Expense expense){
        return ResponseEntity.ok(expenseRepository.save(expense));
    }

    @GetMapping("/expense")
    public ResponseEntity<List<Expense>> getExpense(@PathVariable int userId){
        return ResponseEntity.ok(expenseRepository.findByUser(userRepository.findById(userId)));
    }
}
