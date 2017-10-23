package hu.bme.fitnessapplication.server.repository.event.model.dto;

import java.util.Date;
import java.util.List;

import hu.bme.fitnessapplication.server.repository.event.model.Event;
import org.springframework.beans.factory.annotation.Autowired;

import hu.bme.fitnessapplication.server.BaseDTO;
import hu.bme.fitnessapplication.server.repository.user.model.User;
import hu.bme.fitnessapplication.server.repository.user.UserRepository;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventResponseDTO extends BaseDTO<Event> {
	private static final long serialVersionUID = -4395695309357330622L;

	protected String title;
	protected String creatorUsername;
	protected String creatorDisplayName;
	protected Date startDate;
	protected Date endDate;
	protected List<User> participants;
	
    @Autowired
    protected UserRepository userRepository;

	public EventResponseDTO() {

	}

	public EventResponseDTO(Event event) {
		if (event != null) {
			if (event.getId() != null) {
				this.id = event.getId().toString();
			}
			this.title = event.getTitle();
			this.creatorUsername = event.getCreator().getUsername();
			this.creatorDisplayName = event.getCreator().getDisplayName();
			this.participants = event.getParticipants();
			this.startDate = event.getStartDate();
			this.endDate = event.getEndDate();
		}
	}

	@Override
	public Event toEntity() {
		return null;
	}
}
