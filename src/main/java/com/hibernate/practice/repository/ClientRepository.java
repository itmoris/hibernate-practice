package com.hibernate.practice.repository;

import com.hibernate.practice.entity.Client;
import jakarta.persistence.EntityManager;

public class ClientRepository extends BaseRepository<Long, Client> {
    public ClientRepository(EntityManager entityManager) {
        super(entityManager, Client.class);
    }
}
