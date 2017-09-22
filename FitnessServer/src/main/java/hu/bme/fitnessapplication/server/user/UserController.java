package hu.bme.fitnessapplication.server.user;

import hu.bme.fitnessapplication.server.BaseService;
import hu.bme.fitnessapplication.server.user.data.User;
import hu.bme.fitnessapplication.server.user.data.UserDTO;
import hu.bme.fitnessapplication.server.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Returns the currently logged in user
     * @return
     */
    @RequestMapping(path = "/me", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getMe() {
        User me = userService.getLoggedInUser();
        if (me != null) {
            return ResponseEntity.ok(new UserDTO(me));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.GET )
    @Secured("ROLE_ADMIN")
    public ResponseEntity getAllUsers() {
       List<User> users = userService.findAll();
       List<UserDTO> result = new ArrayList<>();

       for (User user : users) {
           result.add(new UserDTO(user));
       }

       return ResponseEntity.ok(result);
    }


    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String userId) {
        try {
            UUID id = UUID.fromString(userId);
            User user = userService.findById(id);

            if (user != null) {
                return ResponseEntity.ok(new UserDTO(user));
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @Secured("ROLE_ADMIN")
    public ResponseEntity registerUser(@RequestBody UserDTO userDTO) {
        if (userDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            User user = userDTO.toEntity();
            user = userService.create(user);
            return ResponseEntity.ok(new UserDTO(user));
        } catch (UserService.UsernameAlreadyTakenException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (BaseService.InvalidEntityException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.PUT)
    @Secured("ROLE_ADMIN")
    public ResponseEntity updateUser(@PathVariable String userId, @RequestBody UserDTO userDTO) {
        if (userDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            UUID id = UUID.fromString(userId);
            User updatedUser = userDTO.toEntity();
            updatedUser = userService.update(id, updatedUser);
            return ResponseEntity.ok(new UserDTO(updatedUser));
        } catch (BaseService.EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BaseService.InvalidEntityException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.DELETE)
    @Secured("ROLE_ADMIN")
    public ResponseEntity deleteUser(@PathVariable String userId) {
        try {
            UUID id = UUID.fromString(userId);
            userService.delete(id);
            return ResponseEntity.ok().build();
        } catch (BaseService.EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
