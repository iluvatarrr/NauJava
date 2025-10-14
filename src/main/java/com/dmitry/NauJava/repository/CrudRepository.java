package com.dmitry.NauJava.repository;

import com.dmitry.NauJava.domain.exception.ResourceNotFoundException;
/**Crud репозиторий с дженериками для гибкости
 **/
public interface CrudRepository<T, ID> {
    void create(T entity);
    T read(ID id) throws ResourceNotFoundException;
    void update(T entity) throws ResourceNotFoundException;
    void delete(ID id) throws ResourceNotFoundException;
}