package pl.com.bottega.hrs.ui.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.bottega.hrs.application.CommandGateway;
import pl.com.bottega.hrs.application.users.*;
import pl.com.bottega.hrs.infrastructure.Secured;

import java.util.Optional;

/**
 * Created by user on 26.11.2017.
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    private CommandGateway commandGateway;
    private CurrentUser currentUser;

    public UsersController(CommandGateway commandGateway, CurrentUser currentUser) {
        this.commandGateway = commandGateway;
        this.currentUser = currentUser;
    }

    @PostMapping
    @Secured(roles = Role.ADMINISTRATOR)
    public void register(@RequestBody RegisterUserCommand cmd) {
        commandGateway.execute(cmd);
    }

    @PutMapping("/{id}")
    public void updateProfile(@PathVariable Integer id, @RequestBody UpdateUserProfileCommand cmd) {
        cmd.setUserId(id);
        commandGateway.execute(cmd);
    }

    @PutMapping("/session")
    public void login(@RequestBody LoginCommand cmd) {
        commandGateway.execute(cmd);
    }

    @GetMapping("/current")
    @Secured
    public ResponseEntity<UserDto> getCurrent() {
        Optional<UserDto> userDtoOptional = currentUser.getUserInfo();
        if (userDtoOptional.isPresent())
            return new ResponseEntity<>(userDtoOptional.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/session")
    public void logout() {
        currentUser.logout();
    }
}
