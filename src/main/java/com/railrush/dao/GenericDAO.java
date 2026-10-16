package com.railrush.dao;

import java.util.List;

/**
 * Generic Data Access Object interface demonstrating Generics in Java (Exp 04).
 *
 * @param <T> Entity type
 * @param <ID> Primary key identifier type
 */
public interface GenericDAO<T, ID> {
    void save(T entity) throws Exception;
    T findById(ID id) throws Exception;
    List<T> findAll() throws Exception;
    void update(T entity) throws Exception;
    void deleteById(ID id) throws Exception;
}
