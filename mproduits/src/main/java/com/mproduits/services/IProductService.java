package com.mproduits.services;

import java.util.List;

import com.mproduits.exceptions.ProductNotFoundException;
import com.mproduits.models.dto.ProductRequest;
import com.mproduits.models.entities.Product;

public interface IProductService {
    List<Product> findAll();
    Product find(Long id) throws ProductNotFoundException;
    Product add(ProductRequest commandeRequest);
    Product update(Long id, ProductRequest commandeRequest) throws ProductNotFoundException;
    void delete(Long id);
}
