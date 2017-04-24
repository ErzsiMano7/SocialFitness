package hu.bme.fitnessapplication.auth.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.bme.fitnessapplication.user.User;
import hu.bme.fitnessapplication.user.claims.UserClaims;
import hu.bme.fitnessapplication.user.claims.UserClaimsMapper;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserClaimsMapper userClaimsMapper;

    @RequestMapping("/me")
    public User me() {
        UserClaims userClaims = (UserClaims) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return userClaimsMapper.mapFrom(userClaims);
    }
}
