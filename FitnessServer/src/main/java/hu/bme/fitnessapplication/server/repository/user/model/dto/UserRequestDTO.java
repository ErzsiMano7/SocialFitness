package hu.bme.fitnessapplication.server.repository.user.model.dto;

import hu.bme.fitnessapplication.server.BaseDTO;
import hu.bme.fitnessapplication.server.repository.user.model.Role;
import hu.bme.fitnessapplication.server.repository.user.model.User;
import hu.bme.fitnessapplication.server.repository.user.model.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO extends BaseDTO<User> {
	private static final long serialVersionUID = -1410169654429767330L;

	protected String username;

    protected String password;
    
    protected String displayName;

    protected String role;

    public UserRequestDTO() {

    }

    public UserRequestDTO(User user) {
        if (user != null) {
            if (user.getId() != null) {
                this.id = user.getId().toString();
            }
            this.username = user.getUsername();
            this.role = user.getRole().getRole().name();
            this.displayName = user.getDisplayName();
        }
    }

    @Override
    public User toEntity() {
        User user = new User();

        user.setUsername(username);
        user.setPassword(password); //This will be encrypted later
        user.setDisplayName(displayName);
        user.setRole(new UserRole(user, Role.valueOf(role)));

        return user;
    }
}
