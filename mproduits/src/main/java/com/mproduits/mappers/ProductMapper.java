package com.mproduits.mappers;

import org.springframework.stereotype.Component;

import com.mproduits.models.dto.ProductRequest;
import com.mproduits.models.entities.Product;

@Component
public class ProductMapper implements IMapper<Product, ProductRequest>{

    @Override
    public Product dtoToObject(ProductRequest dto) {
        return new Product(
            null,
            dto.titre(),
            dto.description(), 
            dto.image(), 
            dto.prix());
    }

    @Override
    public ProductRequest objectToDto(Product object) {
        return new ProductRequest(
            object.getTitre(), 
            object.getDescription(), 
            object.getImage(), 
            object.getPrix());
    }
}
