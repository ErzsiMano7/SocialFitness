package hu.bme.fitnessapplication.server.repository.user.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "fitness_gym")
public class Gym extends User {
	private static final long serialVersionUID = 7498887229238389754L;

	@OneToMany
	private List<Trainer> trainers;
	@OneToMany
    private List<User> users;
	
	public Gym()
	{}

	public Gym(String username, String password, String displayName, UserRole role, List<Trainer> trainers, List<User> users)
	{
		super(username, password, displayName, role);
		this.trainers = trainers;
		this.users = users;
	}
}
