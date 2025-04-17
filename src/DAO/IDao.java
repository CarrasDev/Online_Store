package DAO;

import java.util.Collection;
import java.util.Optional;

public interface IDao<T> {

    Optional<T> getById(String id);
    Optional<T> get(T t);
    Collection<T> getAll();
    void save(Object o);
    void update(T t);
    void delete(T t);
}