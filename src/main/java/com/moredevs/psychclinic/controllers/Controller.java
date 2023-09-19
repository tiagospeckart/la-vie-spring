package com.moredevs.psychclinic.controllers;

import com.moredevs.psychclinic.exceptions.NotFoundException;

import java.util.List;

public interface Controller<T> {
    public T findById(int id);
    public List<T> listAll();
    public void save(T entity) throws NotFoundException;
    public void delete(int id);
}
