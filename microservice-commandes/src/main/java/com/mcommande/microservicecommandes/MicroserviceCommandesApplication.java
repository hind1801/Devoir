package com.mcommande.microservicecommandes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.mcommande.microservicecommandes.dao.ProduitProxy;

@SpringBootApplication
@EnableConfigurationProperties
@EnableDiscoveryClient
@EnableFeignClients
public class MicroserviceCommandesApplication implements CommandLineRunner {

    @Autowired
    ProduitProxy produitProxy;

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceCommandesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

      
        System.out.println(produitProxy.recupererUnProduit(3L).getDescription());

        



    }

}
