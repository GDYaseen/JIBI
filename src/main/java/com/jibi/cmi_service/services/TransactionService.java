package com.jibi.cmi_service.services;

import com.jibi.cmi_service.models.Transaction;
import com.jibi.cmi_service.repos.TransactionRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import java.util.List;

@Transactional
@AllArgsConstructor
@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    public Transaction addTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Transaction Not Found"));
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}

