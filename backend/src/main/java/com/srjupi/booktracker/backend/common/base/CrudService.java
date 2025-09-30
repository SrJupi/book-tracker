package com.srjupi.booktracker.backend.common.base;

import java.util.Optional;

public interface CrudService<T, ID> {
    Optional<T> findById(ID id);
    T save(T entity);
    void deleteById(ID id);
}
