package com.moredevs.psychclinic.controllers;

import com.moredevs.psychclinic.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface Controller<T> {

    // Returns the resource by ID with its metadata
    ResponseEntity<T> findById(Integer id);

    // Creates the resource and returns 201 Created along with Location header
    ResponseEntity<T> create(T t);
    // Update resource by ID and return 200 OK or appropriate status code
    ResponseEntity<T> updateById(Integer id, T t);

    // Lists all resources with additional metadata like count
    ResponseEntity<List<T>> listAll();

    // Deletes the resource by ID and returns 204 No Content
    ResponseEntity<Void> deleteById(Integer id);
}

