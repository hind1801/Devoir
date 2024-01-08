package com.mproduits.exceptions;

public class ProductNotFoundException extends Exception{

    public ProductNotFoundException(Long id){
        super("The product with the ID " + id + " was not found.");
    }

}
