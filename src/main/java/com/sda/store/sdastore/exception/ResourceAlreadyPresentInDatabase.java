package com.sda.store.sdastore.exception;

public class ResourceAlreadyPresentInDatabase extends RuntimeException{
    public ResourceAlreadyPresentInDatabase(String message){
        super(message);
    }
}
