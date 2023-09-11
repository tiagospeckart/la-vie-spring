package com.moredevs.psychclinic.controllers.interfaces;

import java.util.List;

public interface IController<T> {
    public void save(T entity) throws Exception;
    public void delete(int id);
    public T findById(int id);
    public List<T> listAll();
}
