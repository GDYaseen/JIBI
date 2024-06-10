package com.jibi.back_end.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jibi.back_end.models.User;
import com.jibi.back_end.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllBySender(User sender);
    List<Transaction> findAllBySenderOrReceiver(User sender,User receiver);

    
    @Query(value = "SELECT " +
    "    DATE_FORMAT(t.transactionDate, '%Y-%m') AS month, " +
    "    SUM(CASE WHEN t.receiver_id = :clientProfId THEN t.amount ELSE 0 END) AS earned, " +
    "    SUM(CASE WHEN t.sender_id = :clientProfId THEN t.amount ELSE 0 END) AS spent " +
    "FROM " +
    "    transaction t " +
    "WHERE " +
    "    (t.receiver_id = :clientProfId OR t.sender_id = :clientProfId) AND " +
    "    t.transactionDate >= DATE_SUB(CURDATE(), INTERVAL 7 MONTH) " +
    "GROUP BY " +
    "    DATE_FORMAT(t.transactionDate, '%Y-%m') " +
    "ORDER BY " +
    "    month DESC", nativeQuery = true)
List<Object[]> getClientProfTransactionsByMonth(@Param("clientProfId") Long clientProfId);
}