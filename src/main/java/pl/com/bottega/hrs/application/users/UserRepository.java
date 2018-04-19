package pl.com.bottega.hrs.application.users;

import pl.com.bottega.hrs.application.users.User;

import javax.jws.soap.SOAPBinding;
import java.util.Optional;

/**
 * Created by user on 26.11.2017.
 */
public interface UserRepository {

    boolean isLoginOccupied(String login);

    void save(User user);

    Optional<User> get(String login);

    User get(Integer number);

    User get(String login, String password);
}
