package hu.bme.fitnessapplication.data.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class UserClaims implements Serializable {

    public static final String USERNAME_KEY = "username";

    public static final String ROLES_KEY = "roles";

    private String id;

    private String username;

    private List<String> roles;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public UserClaims() {

    }

    public UserClaims(String id, String username, List<String> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }
}
