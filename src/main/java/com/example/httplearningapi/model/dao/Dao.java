package com.example.httplearningapi.model.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> getById(int id);
    List<T> getAll();
}
