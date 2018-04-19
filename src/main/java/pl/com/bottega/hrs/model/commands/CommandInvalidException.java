package pl.com.bottega.hrs.model.commands;

/**
 * Created by user on 20.11.2017.
 */
public class CommandInvalidException extends RuntimeException {

    private ValidationErrors validationErrors;

    public CommandInvalidException(ValidationErrors validationErrors) {
        this.validationErrors = validationErrors;
    }

    public ValidationErrors getValidationErrors() {
        return validationErrors;
    }
}
