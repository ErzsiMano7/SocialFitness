package hu.bme.fitnessapplication.server.user.service;

import hu.bme.fitnessapplication.server.user.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Spring auto-generates the user repository implementation using this interface.
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);
}
