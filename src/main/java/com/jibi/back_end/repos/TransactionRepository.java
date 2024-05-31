package com.jibi.back_end.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jibi.back_end.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}