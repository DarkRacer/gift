package com.gift.repository;

import com.gift.model.entities.ProductTransaction;
import com.gift.model.entities.Transaction;
import com.gift.model.projections.Count;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for the transactions products
 *
 * @author Maksim Shcherbakov
 */
public interface ProductTransactionRepo extends JpaRepository<ProductTransaction, Transaction> {
    @Modifying
    @Query(value = "insert into gift.products_transaction (tid, product_id) values (:tid, :productId)",
                                    nativeQuery = true)
    void save(@Param("tid") Long tid, @Param("productId") Long productId);

    @Query(value = "select pt.product_id, COUNT(pt.product_id) as count from gift.products_transaction pt " +
            "group by pt.product_id order by count desc", nativeQuery = true)
    List<Count> findCountProduct ();
}
