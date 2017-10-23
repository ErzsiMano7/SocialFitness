package hu.bme.fitnessapplication.server.repository.event;

import hu.bme.fitnessapplication.server.BaseEntity;
import hu.bme.fitnessapplication.server.repository.user.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "fitness_event")
public class Event extends BaseEntity {
	private static final long serialVersionUID = -1402378601146226280L;
	
	@Column(nullable = false)
	private String title;
	
	@ManyToOne
	private User creator;
	
	@Column(nullable = false)
	private Date startDate;
	
	@Column(nullable = false)
	private Date endDate;
	
	@ManyToMany
	private List<User> participants;
	
	public Event()
	{}
	
	public Event(String title, User creator, Date startDate, Date endDate, List<User> participants) {
		this.title = title;
		this.creator = creator;
		this.startDate = startDate;
		this.endDate = endDate;
		this.participants = participants;
	}
	
	
}
