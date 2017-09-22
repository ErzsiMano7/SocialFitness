package hu.bme.fitnessapplication.server.user.data;


import hu.bme.fitnessapplication.server.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "fitness_user")
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<UserRole> roles = new ArrayList<>();

    public User() {

    }

    public User(String username, String password, List<UserRole> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
