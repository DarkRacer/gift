package com.gift.model.projections;

import com.gift.model.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectionsHistory {
    private Long transactionId;
    private String name;
    private String url;
    private String photo_100;
    private Set<Product> gifts;
}
