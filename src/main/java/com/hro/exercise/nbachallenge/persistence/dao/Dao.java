package com.hro.exercise.nbachallenge.persistence.dao;

import com.hro.exercise.nbachallenge.persistence.model.Model;

import java.util.List;

public interface Dao<T extends Model> {

    List<T> findAll();
    T findById(Integer id);
    T saveOrUpdate(T modelObject);
    void delete(Integer id);

}
