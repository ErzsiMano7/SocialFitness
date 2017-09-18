package hu.bme.fitnessapplication.repository.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.bme.fitnessapplication.data.user.User;


public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);

}
