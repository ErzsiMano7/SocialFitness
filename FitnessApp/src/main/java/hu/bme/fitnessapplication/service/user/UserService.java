package hu.bme.fitnessapplication.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hu.bme.fitnessapplication.data.user.Role;
import hu.bme.fitnessapplication.data.user.User;
import hu.bme.fitnessapplication.data.user.UserRole;
import hu.bme.fitnessapplication.repository.user.UserRepository;
import lombok.extern.apachecommons.CommonsLog;

@Service
//@CommonsLog
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(String username, String password, List<Role> roles) throws UserAlreadyExistsException {
//        log.info("Registering " + username + " with roles: " + roles.toArray());

        if(userRepository.findByUsername(username) != null) {
            throw new UserAlreadyExistsException();
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        List<UserRole> userRoles = new ArrayList<>();
        for(Role role : roles) {
            userRoles.add(new UserRole(user, role));
        }
        user.setRoles(userRoles);

        return userRepository.save(user);
    }

    public User login(String username, String password) throws BadPasswordException, UserNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user != null) {
            if(passwordEncoder.matches(password, user.getPassword())) {
                return user;
            } else {
                throw new BadPasswordException();
            }
        } else {
            throw new UserNotFoundException();
        }
    }

    public class UserAlreadyExistsException extends Exception {

    }

    public class UserNotFoundException extends Exception {

    }

    public class BadPasswordException extends Exception {

    }

}
