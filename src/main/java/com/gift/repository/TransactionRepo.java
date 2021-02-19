package com.gift.repository;

import com.gift.model.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the transactions
 *
 * @author Maksim Shcherbakov
 */
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
}
