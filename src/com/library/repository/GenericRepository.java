package com.library.repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface GenericRepository<T, ID> {

    List<T> findAll() throws IOException, ClassNotFoundException;

    Optional<T> findById(ID id) throws IOException, ClassNotFoundException;

    void save(T entity) throws IOException, ClassNotFoundException;

    void update(T entity) throws IOException, ClassNotFoundException;

    void delete(ID id) throws IOException, ClassNotFoundException;
}
