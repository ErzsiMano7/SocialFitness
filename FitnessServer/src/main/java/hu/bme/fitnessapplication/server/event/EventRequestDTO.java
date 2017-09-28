package hu.bme.fitnessapplication.server.event;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import hu.bme.fitnessapplication.server.BaseDTO;
import hu.bme.fitnessapplication.server.user.data.User;
import hu.bme.fitnessapplication.server.user.service.UserRepository;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventRequestDTO extends BaseDTO<Event> {
	private static final long serialVersionUID = -4395695309357330622L;

		protected String title;
	    protected Date startDate;
	    protected Date endDate;
	    protected List<User> participants;
	    
	    public EventRequestDTO() {

	    }

	    public EventRequestDTO(Event event) {
	        if (event != null) {
	            if (event.getId() != null) {
	                this.id = event.getId().toString();
	            }
	            this.title = event.getTitle();
	            this.participants = event.getParticipants();
	            this.startDate = event.getStartDate();
	            this.endDate = event.getEndDate();
	        }
	    }

	    @Override
	    public Event toEntity() {
	        Event event = new Event();

	        event.setTitle(title);
	        event.setStartDate(startDate);
	        event.setEndDate(endDate);
	        event.setParticipants(participants);
	        
	        return event;
	    }
	}

