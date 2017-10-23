package hu.bme.fitnessapplication.server.endpoint.event;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import hu.bme.fitnessapplication.server.repository.event.Event;
import hu.bme.fitnessapplication.server.repository.event.dto.EventRequestDTO;
import hu.bme.fitnessapplication.server.repository.event.dto.EventResponseDTO;
import hu.bme.fitnessapplication.server.repository.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hu.bme.fitnessapplication.server.BaseService;
import hu.bme.fitnessapplication.server.repository.user.model.User;
import hu.bme.fitnessapplication.server.repository.user.UserRepository;
import hu.bme.fitnessapplication.server.repository.user.UserService;

@RestController
@RequestMapping("/events")
public class EventController {
	@Autowired
	private EventService eventService;

	@Autowired
	private UserRepository userRepository;
	/**
	 * Returns the currently logged in user
	 * 
	 * @return
	 */
	
	@RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Authentication authentication) {
		User user = userRepository.findByUsername(authentication.getName());
        return authentication.getName();
    }

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity getEvents() {
		List<Event> events = eventService.findAll();
		List<EventResponseDTO> result = new ArrayList<>();

		for (Event event : events) {
			result.add(new EventResponseDTO(event));
		}

		return ResponseEntity.ok(result);
	}

	@RequestMapping(path = "/{eventId}", method = RequestMethod.GET)
	public ResponseEntity getTitleById(@PathVariable String eventId) {
		try {
			UUID id = UUID.fromString(eventId);
			Event event = eventService.findById(id);

			if (event != null) {
				return ResponseEntity.ok(new EventResponseDTO(event));
			} else {
				return ResponseEntity.notFound().build();
			}

		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity registerEvent(@RequestBody EventRequestDTO eventDTO) {
		if (eventDTO == null) {
			return ResponseEntity.badRequest().build();
		}

		try {
			Event event = eventDTO.toEntity();
			event = eventService.create(event);
			return ResponseEntity.ok(new EventResponseDTO(event));
		} catch (UserService.UsernameAlreadyTakenException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (BaseService.InvalidEntityException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@RequestMapping(path = "/{eventId}", method = RequestMethod.PUT)
	public ResponseEntity updateEvent(@PathVariable String eventId, @RequestBody EventRequestDTO eventDTO, Authentication authentication) {
		if (eventDTO == null) {
			return ResponseEntity.badRequest().build();
		}
		
		try {
			User user = userRepository.findByUsername(authentication.getName());
			
			UUID id = UUID.fromString(eventId);
			Event updatedEvent = eventDTO.toEntity();
			
			if(!updatedEvent.getCreator().equals(user))
			{
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			updatedEvent = eventService.update(id, updatedEvent);
			return ResponseEntity.ok(new EventResponseDTO(updatedEvent));
		} catch (BaseService.EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (BaseService.InvalidEntityException | IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@RequestMapping(path = "/{eventId}", method = RequestMethod.DELETE)
	public ResponseEntity deleteEvent(@PathVariable String eventId) {
		try {
			UUID id = UUID.fromString(eventId);
			
			eventService.delete(id);
			return ResponseEntity.ok().build();
		} catch (BaseService.EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
