package com.gift.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Class describes the transactions entity
 *
 * @author Maksim Shcherbakov
 */
@Entity
@Table(schema = "gift", name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TID")
    private Long id;

    @Column(name = "recipient")
    private String recipient;

    public Transaction(String recipient) {
        this.recipient = recipient;
    }
}
