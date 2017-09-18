package hu.bme.fitnessapplication.data.user;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import hu.bme.fitnessapplication.data.BaseEntity;

@Entity
public class UserRole extends BaseEntity {

    @Id
    @GeneratedValue
    protected UUID id;
    
    @ManyToOne
    protected User user;

    @Enumerated(EnumType.STRING)
    protected Role role;

    public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

    public UserRole() {

    }

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }
}
