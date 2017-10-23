package hu.bme.fitnessapplication.server.messaging;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface MessagesRepository extends CrudRepository<ChatMessage, String>{
	List<ChatMessage> findBySender(UUID sender);
	
	List<ChatMessage> findByRecipient(UUID recipient);
	
	List<ChatMessage> findBySenderAndRecipient(UUID sender, UUID recipient);
	
}
