package hu.bme.fitnessapplication.server.repository.event;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.bme.fitnessapplication.server.BaseService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventService implements BaseService<Event> {
	  @Autowired
	    private EventRepository eventRepository;

	@Override
	public Event create(Event entity) {
		validate(entity);
		 if (eventRepository.findByTitle(entity.getTitle()) != null) {
	            throw new EventTitleAlreadyExistsException();
	        }

	        return eventRepository.save(entity);
	}

	@Override
	public List<Event> findAll() {
		return eventRepository.findAll();
	}

	@Override
	public Event findById(UUID id) {
		return eventRepository.findOne(id);
	}

	@Override
	public Event update(UUID id, Event updated) {
		Event existing = eventRepository.findOne(id);

        if (existing == null) {
            throw new EntityNotFoundException();
        }

        validate(updated);

        existing.setTitle(updated.getTitle());
        existing.setStartDate(updated.getStartDate());
        existing.setEndDate(updated.getEndDate());
        existing.setParticipants(updated.getParticipants());
        
        return eventRepository.save(existing);
	}

	@Override
	public void delete(UUID id) {
		if (eventRepository.exists(id)) {
            eventRepository.delete(id);
        } else {
            throw new EntityNotFoundException();
        }
	}
	
	    private void validate(Event event) {
	        if (event == null) {
	            throw new InvalidEntityException("Given entity is null!");
	        } else {
	        	 if (event.getTitle() == null) {
		                throw new InvalidEntityException("Title must be set!");
		            }
	            if (event.getCreator() == null) {
	                throw new InvalidEntityException("Creator must be set!");
	            }
	            if (event.getStartDate() == null) {
	                throw new InvalidEntityException("Start date must be set!");
	            }
	            if (event.getEndDate() == null) {
	                throw new InvalidEntityException("End date must be set!");
	            }
	            if (event.getParticipants() == null || event.getParticipants().isEmpty()) {
	                throw new InvalidEntityException("Participants must be set!");
	            }
	        }
	    }
	    public class EventTitleAlreadyExistsException extends RuntimeException {

	    }

}
