package ru.instamart.jdbc.dao;

import java.util.List;
import java.util.Optional;

public class GooseDbVersionDao implements Dao {
    @Override
    public boolean delete(Object id) {
        return false;
    }

    @Override
    public Object save(Object ticket) {
        return null;
    }

    @Override
    public void update(Object ticket) {

    }

    @Override
    public Optional findById(Object id) {
        return Optional.empty();
    }

    @Override
    public List findAll() {
        return null;
    }
}
