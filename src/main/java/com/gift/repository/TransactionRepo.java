package com.gift.repository;

import com.gift.model.entities.Transaction;
import com.gift.model.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for the transactions
 *
 * @author Maksim Shcherbakov
 */
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    @Query(value = "select t.* from gift.transactions t where t.sender_id = :userId and t.is_wish = true", nativeQuery = true)
    List<Transaction> findTransactionsWish(@Param("userId") Long userId);

    @Query(value = "select t.* from gift.transactions t where t.sender_id = :userId and t.is_wish = false", nativeQuery = true)
    List<Transaction> findTransactionsBySender(@Param("userId") Long userId);

    @Query(value = "select t.* from gift.transactions t where t.tid = :id", nativeQuery = true)
    Transaction findTransactionById(@Param("id") Long id);
}
