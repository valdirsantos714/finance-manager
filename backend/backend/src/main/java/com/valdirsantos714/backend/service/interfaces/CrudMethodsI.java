package com.valdirsantos714.backend.service.interfaces;

import java.util.List;

public interface CrudMethodsI<T, ID> {
    T create(T entity);
    T read(ID id);
    T update(ID entity);
    void delete(ID id);
    List<T> findAll();
}
