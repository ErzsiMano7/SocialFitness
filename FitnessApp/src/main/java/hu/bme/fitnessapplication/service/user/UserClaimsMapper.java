package hu.bme.fitnessapplication.service.user;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import hu.bme.fitnessapplication.data.user.User;
import hu.bme.fitnessapplication.data.user.UserClaims;
import io.jsonwebtoken.Claims;

@Service
public class UserClaimsMapper {

    @Value("${jwt.secretKey}")
    private String secretKey;

    public UserClaims mapTo(Claims claims) {

        UserClaims userClaims = new UserClaims();
        userClaims.setId(claims.getSubject());
        userClaims.setUsername((String) claims.get(UserClaims.USERNAME_KEY));
        userClaims.setRoles((List<String>) claims.get(UserClaims.ROLES_KEY));

        return userClaims;
    }

    public User mapFrom(UserClaims userClaims) {
        User user = new User();

        user.setUsername(userClaims.getUsername());
        user.setId(UUID.fromString(userClaims.getId()));

        return user;
    }

}
