package com.mcommande.microservicecommandes.controller;


import com.mcommande.microservicecommandes.configurations.ApplicationPropertiesConfigurations;
import com.mcommande.microservicecommandes.dao.CommandeDao;
import com.mcommande.microservicecommandes.dao.ProduitProxy;
import com.mcommande.microservicecommandes.exception.CommandeAlreadyExist;
import com.mcommande.microservicecommandes.exception.CommandeNotFoundException;
import com.mcommande.microservicecommandes.exception.ProductNotFoundException;
import com.mcommande.microservicecommandes.model.Commande;
import com.mcommande.microservicecommandes.model.ProductBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/commande")
public class CommandeController implements HealthIndicator {

    @Autowired
    CommandeDao dao;
    @Autowired
    ApplicationPropertiesConfigurations propertiesConfigurations;   
    @Autowired
    ProduitProxy produitProxy;
    
    @GetMapping
    public List<Commande> findAll()
    {
        int numberOfDays = propertiesConfigurations.getCommandeslast();
        List<Commande> commandes = dao.findAll();
        commandes.removeIf(cmd ->
        cmd.getDate()
        .isBefore(
            LocalDate.now()
            .minusDays(numberOfDays)));

        return commandes;
    }



    @GetMapping("/{id}")
    public Optional<Commande> findById(@PathVariable long id)
    {
        Optional<Commande> commande = dao.findById(id);
        if(commande.isEmpty())
            throw new CommandeNotFoundException("Commande with ID "+ id+" does not exist");

        return commande;
    }

    @PostMapping
    public Commande save( @RequestBody Commande commande)
    {
        ProductBean product = produitProxy.recupererUnProduit(commande.getIdProduit());
        Optional<Commande> cmd = dao.findById(commande.getId());

        if (cmd.isPresent())
                throw new CommandeAlreadyExist("Commande with ID " + commande.getId() + " already exist");
        if (product == null)
            throw new ProductNotFoundException("Product with ID "+commande.getIdProduit()+" does not exist");        

        return dao.save(commande);
    }


    @DeleteMapping("{id}")
    public String delete(@PathVariable long id)
    {
        Optional<Commande> commande = dao.findById(id);
        if(commande.isEmpty())
            throw new CommandeNotFoundException("");
        dao.deleteById(id);
        return "Deleted !";
    }


    @PutMapping("{id}")
    public Commande update(@PathVariable long id , @RequestBody Commande commande)
    {
        ProductBean product = produitProxy.recupererUnProduit(commande.getId());
        Optional<Commande> updatedCommande = dao.findById(id);

        if(updatedCommande.isEmpty())
            throw new CommandeNotFoundException("Commande with ID "+commande.getId()+" already exist");
        if (product != null)
            throw new ProductNotFoundException("Product with ID "+commande.getIdProduit()+" does not exist");    

        commande.setId(id);

        return dao.save(commande);
    }


    @Override
    public Health health() {
        List<Commande> commandes = dao.findAll();
        if (commandes.isEmpty()) {
            return Health.down().build();
        }
        return Health.up().build();
    }
}
