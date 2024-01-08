package com.mcommande.microservicecommandes.dao;

import com.mcommande.microservicecommandes.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeDao extends JpaRepository<Commande,Long> {
}
