package hu.bme.fitnessapplication.server.repository.user.model;


import hu.bme.fitnessapplication.server.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "fitness_user")
public class User extends BaseEntity {
	private static final long serialVersionUID = 7023168094717955429L;

	@Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String displayName;
    
    @OneToOne(
            mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private UserRole role;

    public User() {

    }

    public User(String username, String password, String displayName, UserRole role) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.role = role;
    }
}
