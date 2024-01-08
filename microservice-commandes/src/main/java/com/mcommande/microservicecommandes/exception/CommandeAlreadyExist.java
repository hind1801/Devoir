package com.mcommande.microservicecommandes.exception;

public class CommandeAlreadyExist extends RuntimeException{
    public CommandeAlreadyExist(String message){super(message);}
}
