package com.mproduits.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mproduits.dao.ProductDao;
import com.mproduits.exceptions.ProductNotFoundException;
import com.mproduits.models.dto.ProductRequest;
import com.mproduits.models.entities.Product;
import com.mproduits.services.IProductService;

@RestController
@RequestMapping("/products")
public class ProductController implements HealthIndicator{

    @Autowired
    ProductDao productDao;

    @Autowired
    IProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) throws ProductNotFoundException{
        Optional<Product> productOptional = Optional.of(productService.find(id));
        return ResponseEntity.of(productOptional);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductRequest Product){
        Product newProduct = productService.add(Product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
        @PathVariable Long id, 
        @RequestBody ProductRequest product
        ) throws ProductNotFoundException {

        Product newProduct = productService.update(id, product);
        Optional<Product> newProductOptional = Optional.of(newProduct);
        return ResponseEntity.of(newProductOptional);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id){
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Override
    public Health health() {
        List<Product> products = productDao.findAll();
        if (products.isEmpty())
            return Health.down().build();
        return Health.up().build();
    }
    
}
