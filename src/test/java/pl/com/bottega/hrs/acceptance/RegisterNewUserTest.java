package pl.com.bottega.hrs.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.bottega.hrs.application.RegisterUserHandler;
import pl.com.bottega.hrs.application.users.UserRepository;
import pl.com.bottega.hrs.model.commands.CommandInvalidException;
import pl.com.bottega.hrs.application.users.RegisterUserCommand;

import static org.junit.Assert.assertTrue;

/**
 * Created by user on 28.11.2017.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RegisterNewUserTest extends AcceptanceTest {

    @Autowired
    private RegisterUserHandler registerUserHandler;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldRegisterNewUser() {
        //given
        RegisterUserCommand registerUserCommand = new RegisterUserCommand();
        registerUserCommand.setLogin("andrzej");
        registerUserCommand.setPassword("password");
        registerUserCommand.setRepeatedPassword("password");

        //when
        registerUserHandler.handle(registerUserCommand);

        //then
        assertTrue(userRepository.isLoginOccupied("andrzej"));
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotRegisterNewUserWithSameLogin() {
        //given
        RegisterUserCommand registerUserCommand = new RegisterUserCommand();
        registerUserCommand.setLogin("andrzej");
        registerUserCommand.setPassword("password");
        registerUserCommand.setRepeatedPassword("password");
        registerUserHandler.handle(registerUserCommand);

        //when
        RegisterUserCommand registerUserCommand2 = new RegisterUserCommand();
        registerUserCommand2.setLogin("andrzej");
        registerUserCommand2.setPassword("password1");
        registerUserCommand2.setRepeatedPassword("password1");
        registerUserHandler.handle(registerUserCommand2);
    }

}
