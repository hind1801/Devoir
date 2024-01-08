package com.mcommande.microservicecommandes.exception;

public class CommandeNotFoundException extends RuntimeException{
    public CommandeNotFoundException(String message){super(message);}
}
