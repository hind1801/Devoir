package com.mproduits.models.dto;

public record ProductRequest(
    String titre,
    String description,
    String image,
    Double prix
) {
    
}
