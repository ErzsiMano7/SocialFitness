package hu.bme.fitnessapplication.server.user.service;

import hu.bme.fitnessapplication.server.BaseService;
import hu.bme.fitnessapplication.server.user.data.Role;
import hu.bme.fitnessapplication.server.user.data.User;
import hu.bme.fitnessapplication.server.user.data.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserService implements BaseService<User> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void injectDummyUser() {
        log.warn("Injecting dummy user!");

        User admin;
        try {
            admin = new User("Admin", "1234", null);
            admin.setRoles(Collections.singletonList(new UserRole(admin, Role.ROLE_ADMIN)));
            create(admin);
        } catch (UsernameAlreadyTakenException e) {
            log.debug("User already in database");
        }
    }

    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            if (username != null && !username.isEmpty()) {
                return userRepository.findByUsername(username);
            } else {
                throw new IllegalStateException("The currently logged in user is not in the database!");
            }
        }

        return null;
    }

    @Override
    public User create(User entity) {

        //Validate
        validate(entity);

        //Encode the password
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));

        if (userRepository.findByUsername(entity.getUsername()) != null) {
            throw new UsernameAlreadyTakenException();
        }

        return userRepository.save(entity);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findOne(id);
    }

    @Override
    public User update(UUID id, User updated) {
        User existing = userRepository.findOne(id);

        if (existing == null) {
            throw new EntityNotFoundException();
        }

        //Validate the updated
        validate(updated);

        //Encode the password
        updated.setPassword(passwordEncoder.encode(updated.getPassword()));

        // Update the existing with the new
        existing.setUsername(updated.getUsername());
        existing.setRoles(updated.getRoles());
        existing.setPassword(updated.getPassword());

        return userRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        if (userRepository.exists(id)) {
            userRepository.delete(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

    private void validate(User user) {
        if (user == null) {
            throw new InvalidEntityException("Given entity is null!");
        } else {
            if (user.getUsername() == null || user.getUsername().isEmpty()) {
                throw new InvalidEntityException("Username must be set!");
            }

            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                throw new InvalidEntityException("Password must be set!");
            }

            if (user.getRoles() == null || user.getRoles().isEmpty()) {
                throw new InvalidEntityException("A user must have at least one role!");
            }
        }
    }

    public class UsernameAlreadyTakenException extends RuntimeException {

    }
}
