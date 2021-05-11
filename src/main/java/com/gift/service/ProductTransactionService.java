package com.gift.service;

import com.gift.api.VK;
import com.gift.model.api.vk.ResponseUserGet;
import com.gift.model.entities.Product;
import com.gift.model.entities.Transaction;
import com.gift.model.projections.Choice;
import com.gift.model.projections.InfoTransaction;
import com.gift.model.projections.SelectionsHistory;
import com.gift.repository.ProductRepo;
import com.gift.repository.ProductTransactionRepo;
import com.gift.repository.TransactionRepo;
import com.gift.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Service for the transactions products
 *
 * @author Maksim Shcherbakov
 */
@Service
public class ProductTransactionService {
    private final ProductTransactionRepo productTransactionRepo;
    private final ProductRepo productRepo;
    private final TransactionRepo transactionRepo;
    private final UserRepository userRepository;
    private final VK vk;

    @Autowired
    public ProductTransactionService(ProductTransactionRepo productTransactionRepo, ProductRepo productRepo,
                                     TransactionRepo transactionRepo, UserRepository userRepository, VK vk) {
        this.productTransactionRepo = productTransactionRepo;
        this.productRepo = productRepo;
        this.transactionRepo = transactionRepo;
        this.userRepository = userRepository;
        this.vk = vk;
    }

    @Transactional
    public void save (Choice choice) {
        Transaction transaction = new Transaction();
        transaction.setRecipient(choice.getRecipient());
        transaction.setSender(userRepository.findUsersById(choice.getSender()));

        if (Objects.equals(vk.findUserId(choice.getRecipient()).getResponse()[0].getId(), Long.valueOf(transaction.getSender().getProviderUserId()))){
            transaction.setWish(true);
        }

        transactionRepo.save(transaction);

        Set<Product> products = new HashSet<>(Arrays.asList(choice.getProducts()));

        for(Product product : products) {
            productTransactionRepo.save(transaction.getId(), product.getId());
            productRepo.promotionCounter(product.getId());
        }
    }

    public Set<Product> loadWish(Long userId) {
        List<Transaction> transactions = transactionRepo.findTransactionsWish(userId);
        if (transactions != null) {
            List<Long> productId = new ArrayList<>();
            for (Transaction transaction : transactions) {
                productId.addAll(productTransactionRepo.findProductTransactionsByTransaction(transaction.getId()));
            }
            return loadProducts(productId);
        }
        return null;
    }

    private Set<Product> loadProducts(List<Long> productId) {
        Set<Product> products = new HashSet<>();

        for (Long id : productId) {
            products.add(productRepo.findProductById(id));
        }

        for (Product product : products) {
            if (product.getDescription() != null) {
                if (product.getDescription().length() > 30) {
                    product.setDescription(product.getDescription().substring(0, 30));
                    product.setDescription(product.getDescription().concat("..."));
                }
            }
        }
        return products;
    }

    public List<SelectionsHistory> getHistory(Long userId) {
        List<SelectionsHistory> selectionsHistories = new ArrayList<>();

        List<Transaction> transactions = transactionRepo.findTransactionsBySender(userId);

        for (Transaction transaction : transactions) {
            if(!transaction.isWish()) {
                ResponseUserGet responseUserGet = vk.findUserId(transaction.getRecipient()).getResponse()[0];
                List<Long> productId = productTransactionRepo.findProductTransactionsByTransaction(transaction.getId());

                selectionsHistories.add(new SelectionsHistory(transaction.getId(), responseUserGet.getFirst_name() + " "
                        + responseUserGet.getLast_name(), transaction.getRecipient(), responseUserGet.getPhoto_100(), loadProducts(productId)));
            }
        }

        return selectionsHistories;
    }

    public void deleteWish(InfoTransaction transactionWish) {
        List<Transaction> transactions = transactionRepo.findTransactionsWish(transactionWish.getId());
        for (Transaction transaction : transactions) {
            productTransactionRepo.delete(transaction.getId(), transactionWish.getProductId());
        }
    }

    @Transactional
    public void saveWish(InfoTransaction infoTransactionWish) {
        if (transactionRepo.findTransactionsWish(infoTransactionWish.getId()) == null) {
            Transaction transactionWish = new Transaction();
            transactionWish.setRecipient("https://vk.com/id" +
                    userRepository.findUsersById(infoTransactionWish.getId()).getProviderUserId());
            transactionWish.setSender(userRepository.findUsersById(infoTransactionWish.getId()));
            transactionWish.setWish(true);
            transactionRepo.save(transactionWish);

        }
        List<Transaction> transactions = transactionRepo.findTransactionsWish(infoTransactionWish.getId());
        for (Transaction transaction : transactions) {
            productTransactionRepo.save(transaction.getId(),
                    productRepo.findProductById(infoTransactionWish.getProductId()).getId());
        }
    }

    public void deleteSelectionsHistory(InfoTransaction transaction) {
        System.out.println(transaction.getId() + " " + transaction.getProductId());
        productTransactionRepo.delete(transactionRepo.findTransactionById(transaction.getId()).getId(), productRepo.findProductById(transaction.getProductId()).getId());
    }

    @Transactional
    public void saveSelectionsHistory(InfoTransaction transaction) {
        productTransactionRepo.save(transactionRepo.findTransactionById(transaction.getId()).getId(), productRepo.findProductById(transaction.getProductId()).getId());
    }
}
