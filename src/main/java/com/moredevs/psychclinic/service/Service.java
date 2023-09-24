package com.moredevs.psychclinic.service;

import com.moredevs.psychclinic.models.dtos.AdminDTO;

import java.util.List;

public interface Service<T> {
    public T update(T t);
    public T findById(Integer id);
    public List<T> listAll();
    public void deleteById(Integer id);
}
