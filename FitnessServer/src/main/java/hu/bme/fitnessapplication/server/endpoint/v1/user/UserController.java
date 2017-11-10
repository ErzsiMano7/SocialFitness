package hu.bme.fitnessapplication.server.endpoint.v1.user;

import hu.bme.fitnessapplication.server.BaseService;
import hu.bme.fitnessapplication.server.endpoint.v1.BaseController;
import hu.bme.fitnessapplication.server.repository.user.model.Role;
import hu.bme.fitnessapplication.server.repository.user.model.User;
import hu.bme.fitnessapplication.server.repository.user.model.dto.UserRequestDTO;
import hu.bme.fitnessapplication.server.repository.user.model.dto.UserResponseDTO;
import hu.bme.fitnessapplication.server.repository.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController("UserController_v1")
@RequestMapping("v1/users")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * Returns the currently logged in user
     *
     * @return
     */
    @RequestMapping(path = "/me", method = RequestMethod.GET)
    public ResponseEntity getMe(Authentication auth) {
        User me = getCallingUser(auth);
        if (me != null) {
            return ResponseEntity.ok(convertToDto(me));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @Secured("ROLE_ADMIN")
    public ResponseEntity getAllUsers() {
        List<User> users = userService.findAll();
        List<UserResponseDTO> result = new ArrayList<>();

        for (User user : users) {
            result.add(convertToDto(user));
        }

        return ResponseEntity.ok(result);
    }


    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    @Secured("ROLE_ADMIN")
    public ResponseEntity getUserById(@PathVariable String userId) {
        try {
            UUID id = UUID.fromString(userId);
            User user = userService.findById(id);

            if (user != null) {
                return ResponseEntity.ok(convertToDto(user));
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity registerUser(@RequestBody UserRequestDTO userDTO) {
        if (userDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            User user = convertToEntity(userDTO);
            user = userService.create(user);
            return ResponseEntity.ok(new UserResponseDTO(user));
        } catch (UserService.UsernameAlreadyTakenException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (BaseService.InvalidEntityException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@PathVariable String userId, @RequestBody UserRequestDTO userDto) {
        if (userDto == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            UUID id = UUID.fromString(userId);
            User updatedUser = convertToEntity(userDto);
            updatedUser = userService.update(id, updatedUser);
            return ResponseEntity.ok(convertToDto(updatedUser));
        } catch (BaseService.EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BaseService.InvalidEntityException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable String userId, Authentication auth) {
        User callingUser = getCallingUser(auth);
        if (callingUser != null) {
            UUID id = UUID.fromString(userId);
            if (callingUser.getId().equals(id) ||
                    callingUser.getRole().getRole() == Role.ROLE_ADMIN) {
                userService.delete(id);
            }
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public ResponseEntity getUsers() {
        List<User> users = userService.findAll();
        List<UserResponseDTO> result = new ArrayList<>();

        for (User user : users) {
            if (user.getRole().getRole().name().equals(Role.ROLE_USER))
                result.add(new UserResponseDTO(user));
        }

        return ResponseEntity.ok(result);
    }

    private User convertToEntity(UserRequestDTO userReq) {
        User user = modelMapper.map(userReq, User.class);
        return user;
    }

    private UserResponseDTO convertToDto(User user) {
        UserResponseDTO dto = modelMapper.map(user, UserResponseDTO.class);
        dto.setRole(user.getRole().getRole().name());
        return dto;
    }

    protected User getCallingUser(Authentication auth) {
        return userService.findByUsername(auth.getName());
    }
}
