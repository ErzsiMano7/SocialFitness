package hu.bme.fitnessapplication.server.repository.user.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "fitness_trainer")
public class Trainer extends User {
	private static final long serialVersionUID = 160691593402808310L;

	@OneToMany
	private List<User> users;
	
	public Trainer()
	{}

	public Trainer(String username, String password, String displayName, UserRole role, List<User> users)
	{
		super(username, password, displayName, role);
		this.users = users;
	}
}