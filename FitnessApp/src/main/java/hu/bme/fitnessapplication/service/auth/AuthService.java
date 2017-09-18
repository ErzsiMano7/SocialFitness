package hu.bme.fitnessapplication.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.bme.fitnessapplication.data.auth.AuthRequest;
import hu.bme.fitnessapplication.data.auth.AuthToken;
import hu.bme.fitnessapplication.data.user.User;
import hu.bme.fitnessapplication.service.user.UserService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthTokenMapper authTokenMapper;

    public AuthToken authenticateUser(AuthRequest userLogin) {
        try {
//            log.debug("Authenticating {} : {}", userLogin.getUsername(), userLogin.getPassword().replaceAll(".", "*"));
            User user = userService.login(userLogin.getUsername(), userLogin.getPassword());
            return authTokenMapper.mapTo(user);
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }
    }

    public class AuthenticationException extends RuntimeException {
        public AuthenticationException(Throwable cause) {
            super(cause);
        }
    }
}
