package com.gift.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Class describes the products entity
 *
 * @author Maksim Shcherbakov
 */
@Entity
@Table(schema = "gift", name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "catalog_url")
    private String catalogUrl;

    @Column(name = "counter")
    private int counter;

    @JoinColumn(name = "category_id")
    @OneToOne(targetEntity = Category.class)
    private Category category;

    @Column(name = "picture_url")
    private String pictureUrl;
}
