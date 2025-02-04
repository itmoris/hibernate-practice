package com.hibernate.practice.repository;

import com.hibernate.practice.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Repository<K extends Serializable, E extends BaseEntity<K>> {

    K save(E entity);

    void update(E entity);

    void delete(E entity);

    Optional<E> findById(K id);

    List<E> findAll();
}
