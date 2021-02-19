package com.gift.model.projections;

import java.math.BigInteger;

/**
 * Projection for processing a request to get the number of transactions for a product
 *
 * @author Maksim Shcherbakov
 */
public interface Count {
    Integer getProduct_Id();
    BigInteger getCount();
}
