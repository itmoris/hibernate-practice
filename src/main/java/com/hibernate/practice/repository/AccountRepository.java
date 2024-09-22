package com.hibernate.practice.repository;

import com.hibernate.practice.entity.Account;
import jakarta.persistence.EntityManager;

import java.util.List;

public class AccountRepository extends BaseRepository<Long, Account> {
    public AccountRepository(EntityManager entityManager) {
        super(entityManager, Account.class);
    }

    public List<Account> findAllByClientId(Long clientId) {
        return getEntityManager().createQuery(
                        "SELECT a FROM Account a WHERE a.client.id = :clientId", Account.class
                )
                .setParameter("clientId", clientId)
                .getResultList();
    }
}
