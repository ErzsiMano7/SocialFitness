package hu.bme.fitnessapplication.server.user.data;

import hu.bme.fitnessapplication.server.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

/**
 * Mapping table between users and roles.
 */
@Entity
@Getter
@Setter
public class UserRole extends BaseEntity {

    @ManyToOne
    protected User user;

    @Enumerated(EnumType.STRING)
    protected Role role;

    public UserRole() {

    }

    public UserRole(User user, Role role){
        this.user = user;
        this.role = role;
    }
}