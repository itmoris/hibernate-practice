package com.hibernate.practice.repository;

import com.hibernate.practice.entity.Transaction;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TransactionRepository extends BaseRepository<Long, Transaction> {
    public TransactionRepository(EntityManager entityManager) {
        super(entityManager, Transaction.class);
    }

    public List<Transaction> findAllByAccountId(Long accountId) {
        EntityManager entityManager = getEntityManager();
        return entityManager.createQuery("SELECT t FROM Transaction t WHERE t.account.id = :accountId", Transaction.class)
                .setParameter("accountId", accountId)
                .getResultList();
    }
}
