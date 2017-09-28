package hu.bme.fitnessapplication.server.event;

import java.util.UUID;
import hu.bme.fitnessapplication.server.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, UUID> {

	Event findByTitle(String title);

}
