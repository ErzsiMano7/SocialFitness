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

@RestController("TrainerController_v1")
@RequestMapping("v1/users/trainers")
public class TrainerController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ResponseEntity getTrainers() {
        List<User> users = userService.findAll();
        List<UserResponseDTO> result = new ArrayList<>();

        for (User user : users) {
            if (user.getRole().getRole().name().equals(Role.ROLE_TRAINER))
                result.add(new UserResponseDTO(user));
        }

        return ResponseEntity.ok(result);
    }
}
