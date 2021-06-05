package com.sda.store.sdastore.config;

import com.sda.store.sdastore.exception.ResourceAlreadyPresentInDatabase;
import com.sda.store.sdastore.exception.ResourceNotFoundInDatabase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = {ResourceAlreadyPresentInDatabase.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage resourceAlreadyPresentInDatabaseException(ResourceAlreadyPresentInDatabase exception){
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(exception.getMessage());
        return errorMessage;
    }

    @ExceptionHandler(value = {ResourceNotFoundInDatabase.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundInDatabase(ResourceNotFoundInDatabase exception) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(exception.getMessage());
        return errorMessage;
    }
}
