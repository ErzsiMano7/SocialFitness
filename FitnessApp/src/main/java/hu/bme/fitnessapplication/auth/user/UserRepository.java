package hu.bme.fitnessapplication.auth.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.bme.fitnessapplication.user.User;


public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);

}
