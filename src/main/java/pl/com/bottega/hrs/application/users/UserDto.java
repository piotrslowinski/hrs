package pl.com.bottega.hrs.application.users;

import java.util.Set;

/**
 * Created by user on 11.12.2017.
 */
public class UserDto {

    private Integer id;
    private String login;
    private Set<Role> roles;

    public UserDto(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.roles = user.getRoles();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
