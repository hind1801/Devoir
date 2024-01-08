package com.mcommande.microservicecommandes.dao;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mcommande.microservicecommandes.model.ProductBean;


@FeignClient(name = "microservice-produits", url = "localhost:9004/microservice-produits")
public interface ProduitProxy {

    @GetMapping("/products/{id}")
    ProductBean recupererUnProduit(@PathVariable Long id);
}