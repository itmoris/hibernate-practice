package com.hibernate.practice.mapper;

public interface Mapper<F, T> {
    T mapFrom(F from);
}
