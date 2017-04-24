package hu.bme.fitnessapplication.auth.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import hu.bme.fitnessapplication.user.User;
import hu.bme.fitnessapplication.user.UserRole;
import hu.bme.fitnessapplication.user.claims.UserClaims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
public class AuthTokenMapper {

    @Value("${jwt.secretKey}")
    private String secretKey;

    public AuthToken mapTo(User entity) {
        List<String> roles = new ArrayList<>();

        for(UserRole userRole : entity.getRoles()) {
            roles.add(userRole.getRole().name());
        }

        String token = Jwts.builder().setSubject(entity.getId().toString()).claim(UserClaims.ROLES_KEY, roles).claim(UserClaims.USERNAME_KEY, entity.getUsername()).signWith(SignatureAlgorithm.HS256, secretKey).compact();
        return new AuthToken(token);
    }

}
