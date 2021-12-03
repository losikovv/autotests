package ru.instamart.jdbc.dao;

import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<K, E> implements Dao<K, E> {

    public boolean delete(final Long id) {
        return false;
    }

    public E save(E ticket) {
        return null;
    }

    public void update(E ticket) {

    }

    public Optional<E> findById(Long id) {
        return Optional.empty();
    }

    public List<E> findAll() {
        return null;
    }
}
