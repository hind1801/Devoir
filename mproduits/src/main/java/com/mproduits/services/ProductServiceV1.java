package com.mproduits.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mproduits.configurations.ApplicationPropertiesConfiguration;
import com.mproduits.dao.ProductDao;
import com.mproduits.exceptions.ProductNotFoundException;
import com.mproduits.mappers.IMapper;
import com.mproduits.models.dto.ProductRequest;
import com.mproduits.models.entities.Product;

@Component
public class ProductServiceV1 implements IProductService{

    @Autowired
    private ProductDao productDAO;

    @Autowired
    private ApplicationPropertiesConfiguration applicationPropertiesConfiguration;

    @Autowired
    private IMapper<Product, ProductRequest> mapper;

    @Override
    public List<Product> findAll() {
        int limit = applicationPropertiesConfiguration.getLimitDeProduits();
        List<Product> products = productDAO.findAll();
        
        if (products.size() > limit)
            products = products.subList(0, limit);

        return products;
    }

    @Override
    public Product find(Long id) throws ProductNotFoundException {
        Optional<Product> productOptional = productDAO.findById(id);
        
        if (productOptional.isEmpty())
            throw new ProductNotFoundException(id);

        return productOptional.get();
    }

    @Override
    public Product add(ProductRequest productRequest) {
        Product product = mapper.dtoToObject(productRequest);
        Product savedProduct = productDAO.save(product);
        return savedProduct;
    }

    @Override
    public Product update(Long id, ProductRequest productRequest) throws ProductNotFoundException {
        if (productDAO.findById(id).isEmpty())
            throw new ProductNotFoundException(id);

        Product product = mapper.dtoToObject(productRequest);
        product.setId(id);

        Product savedProduct = productDAO.save(product);
        return savedProduct;
    }

    @Override
    public void delete(Long id) {
        productDAO.deleteById(id);
    }
    
}
