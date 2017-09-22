package hu.bme.fitnessapplication.server.user.service;

import hu.bme.fitnessapplication.server.user.data.User;
import hu.bme.fitnessapplication.server.user.data.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter service between our domain users and spring security's
 */
@Service
public class FitnessUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        // Convert our domain roles to spring roles
        List<GrantedAuthority> springRoles = new ArrayList<>();
        for (UserRole userRole : user.getRoles()) {
            springRoles.add(new SimpleGrantedAuthority(userRole.getRole().name()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), springRoles);
    }
}
