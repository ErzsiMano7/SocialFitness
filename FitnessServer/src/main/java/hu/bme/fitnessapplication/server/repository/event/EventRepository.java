package hu.bme.fitnessapplication.server.repository.event;

import java.util.UUID;

import hu.bme.fitnessapplication.server.repository.event.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, UUID> {

	Event findByTitle(String title);

}
