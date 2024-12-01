package com.example.moviesapi.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public abstract class EntityService<T, ID> {

    protected JpaRepository<T, ID> repository;

    public EntityService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    public T createFromId(ID id) {
        return findById(id).orElseThrow(() -> new RuntimeException("Entity not found"));
    }

    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    public T create(T entity) {
        return repository.save(entity);
    }

    public Collection<T> findAll() {
        return repository.findAll();
    }

    public void deleteById(ID id) {
        repository.deleteById(id);
    }


}
