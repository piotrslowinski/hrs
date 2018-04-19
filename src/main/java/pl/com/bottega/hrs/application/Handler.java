package pl.com.bottega.hrs.application;

import pl.com.bottega.hrs.model.commands.Command;

/**
 * Created by user on 20.11.2017.
 */
public interface Handler<C extends Command> {

    void handle(C command);

    Class<? extends Command> getSupportedCommandClass();

    default boolean canHandle(Command command) {
        return command.getClass().equals(getSupportedCommandClass());
    }
}
