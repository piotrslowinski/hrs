package pl.com.bottega.hrs.ui.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.com.bottega.hrs.application.users.SecurityException;
import pl.com.bottega.hrs.infrastructure.NoSuchEntityException;
import pl.com.bottega.hrs.model.commands.CommandInvalidException;

/**
 * Created by user on 27.11.2017.
 */
@ControllerAdvice
public class ErrorHandlers {

    @ResponseStatus(code = HttpStatus.NOT_FOUND,
            reason = "Entity with given id does not exist")
    @ExceptionHandler(NoSuchEntityException.class)
    public void handleEntityNotFound() {

    }

    @ResponseStatus(code = HttpStatus.UNAUTHORIZED,
            reason = "No required authentication")
    @ExceptionHandler(SecurityException.class)
    public void handleSecurityException() {

    }

    @ExceptionHandler(CommandInvalidException.class)
    public ResponseEntity handleInvalidCommand(CommandInvalidException ex) {
        return new ResponseEntity(ex.getValidationErrors(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

}