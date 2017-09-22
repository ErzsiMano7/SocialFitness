package hu.bme.fitnessapplication.server.user.data;

import hu.bme.fitnessapplication.server.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDTO extends BaseDTO<User> {

    protected String username;

    protected String password;

    protected List<String> roles;

    public UserDTO() {

    }

    public UserDTO(User user) {
        if (user != null) {
            if (user.getId() != null) {
                this.id = user.getId().toString();
            }
            this.username = user.getUsername();
            this.roles = new ArrayList<>();
            for (UserRole userRole : user.getRoles()) {
                this.roles.add(userRole.getRole().name());
            }
        }
    }

    @Override
    public User toEntity() {
        User user = new User();

        user.setUsername(username);
        user.setPassword(password); //This will be encrypted later
        user.setRoles(new ArrayList<>());

        for (String role : roles) {
            user.getRoles().add(new UserRole(user, Role.valueOf(role)));
        }

        return user;
    }
}
