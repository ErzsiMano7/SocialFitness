package hu.bme.fitnessapplication.server.messaging;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hu.bme.fitnessapplication.server.repository.user.model.User;
import hu.bme.fitnessapplication.server.repository.user.UserRepository;

@Controller
@MessageMapping("/chat")
public class MessageController {

	@Autowired
	MessagesRepository messageRepo;

	@Autowired
	UserRepository userRepo;

	@RequestMapping(name = "", method = RequestMethod.GET)
	public ResponseEntity<List<ChatMessage>> getAllMessages(
			@PathVariable(name = "otherUser", required = true) String otherUser, Authentication auth) {
		User callingUserObj = userRepo.findByUsername(auth.getName());
		User otherUserObj = userRepo.findByUsername(otherUser);

		if (callingUserObj == null || otherUserObj == null) {
			return ResponseEntity.notFound().build();
		}

		List<ChatMessage> messages = new ArrayList<>();
		messages.addAll(messageRepo.findBySenderAndRecipient(callingUserObj.getId(), otherUserObj.getId()));
		messages.addAll(messageRepo.findBySenderAndRecipient(otherUserObj.getId(), callingUserObj.getId()));

		return ResponseEntity.ok(messages);
	}
	
	@RequestMapping(name = "", method = RequestMethod.POST)
	@MessageMapping("/chat/{topic}")
	@SendTo("/topic/messages")
	public OutputMessage send(
	        @DestinationVariable("topic") String topic, Message message)
	        throws Exception {
	    return new OutputMessage(message.getFrom(), message.getText(), topic);
	}
}
