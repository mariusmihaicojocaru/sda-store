package com.sda.store.sdastore.exception;

public class ResourceNotFoundInDatabase extends RuntimeException{
    public ResourceNotFoundInDatabase(String message){
        super(message);
    }
}
