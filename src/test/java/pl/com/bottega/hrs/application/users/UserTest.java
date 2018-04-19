package pl.com.bottega.hrs.application.users;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by user on 28.11.2017.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {

    @Test
    public void shouldUpdateUserProfile() {
        //given
        User user = new User("jan", "password");
        UpdateUserProfileCommand updateUserCommand = new UpdateUserProfileCommand();
        updateUserCommand.setLogin("janek");
        updateUserCommand.setNewPassword("password1");
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ADMINISTRATOR);
        updateUserCommand.setRoles(roles);

        //when
        user.updateProfile(updateUserCommand);

        //then
        assertEquals("janek", user.getLogin());
        assertEquals("password1", user.getPassword());
        assertTrue(roles.equals(user.getRoles()));
    }
}
