package hu.bme.fitnessapplication.server.endpoint.v1.user;

import hu.bme.fitnessapplication.server.repository.user.UserService;
import hu.bme.fitnessapplication.server.repository.user.model.Role;
import hu.bme.fitnessapplication.server.repository.user.model.User;
import hu.bme.fitnessapplication.server.repository.user.model.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController("GymController_v1")
@RequestMapping("v1/users/gyms")
public class GymController {
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getGyms() {
        List<User> users = userService.findAll();
        List<UserResponseDTO> result = new ArrayList<>();

        for (User user : users) {
            if (user.getRole().getRole().name().equals(Role.ROLE_GYM))
                result.add(new UserResponseDTO(user));
        }

        return ResponseEntity.ok(result);
    }
}
