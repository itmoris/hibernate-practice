package com.hibernate.practice.repository;

import com.hibernate.practice.entity.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseRepository<K extends Serializable, E extends BaseEntity<K>> implements Repository<K, E> {
    @Getter
    private final EntityManager entityManager;
    private final Class<E> entityClass;

    @Override
    public K save(E entity) {
        entityManager.persist(entity);
        return entity.getId();
    }

    @Override
    public void update(E entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(E entity) {
        entityManager.remove(entity);
    }

    @Override
    public Optional<E> findById(K id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public List<E> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = cb.createQuery(entityClass);
        criteriaQuery.from(entityClass).fetch("accounts");

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
