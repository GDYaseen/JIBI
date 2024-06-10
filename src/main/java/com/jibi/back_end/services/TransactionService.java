package com.jibi.back_end.services;

import com.jibi.back_end.dto.MonthlyTransactionSummary;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import com.jibi.back_end.models.Transaction;
import com.jibi.back_end.models.User;
import com.jibi.back_end.repos.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@AllArgsConstructor
@Service
public class TransactionService {

    private TransactionRepository transactionRepository;
    private ClientService clientService;
    private ClientProfessionelService clientProfessionelService;

    public Transaction saveTransaction(Transaction transaction) {
        return this.transactionRepository.save(transaction);
    }

    public Transaction getTransactionById(Long id) {
        return this.transactionRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Transaction Not Found"));
    }

    public List<Transaction> getAllTransactions(String phone) {
        User user = findUserByPhoneNumber(phone);
        if (user == null) {
            return null;
        }
        return this.transactionRepository.findAllBySenderOrReceiver(user,user);
    }
    private User findUserByPhoneNumber(String phoneNumber) {
        User user = clientService.getClientByPhoneNumber(phoneNumber);
        if (user == null) {
            user = clientProfessionelService.getClientProfessionelByPhoneNumber(phoneNumber);
        }
        return user;
    }

    public void deleteTransaction(Long id) {
        this.transactionRepository.deleteById(id);
    }

    public List<MonthlyTransactionSummary> getClientProfTransactionsByMonth(Long clientProfId) {
        List<Object[]> results = transactionRepository.getClientProfTransactionsByMonth(clientProfId);
        return results.stream()
                .map(result -> new MonthlyTransactionSummary(
                        (String) result[0], 
                        (BigDecimal) result[1], 
                        (BigDecimal) result[2]
                ))
                .collect(Collectors.toList());
    }
}

