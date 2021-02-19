package com.gift.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Class describes the products transaction entity
 *
 * @author Maksim Shcherbakov
 */
@Entity
@Table(schema = "gift", name = "products_transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductTransaction {
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "products_transaction", joinColumns = @JoinColumn(name = "product_id"),
                inverseJoinColumns = @JoinColumn(name = "tid"))
    private Set<Transaction> transaction;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "products_transaction", joinColumns = @JoinColumn(name = "tid"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products = new HashSet<>();

    public ProductTransaction(Set<Transaction> transaction, Set<Product> products) {
        this.transaction = transaction;
        this.products = products;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}

