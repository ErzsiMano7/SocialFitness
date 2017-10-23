package hu.bme.fitnessapplication.server.messaging;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("chatmessage")
public class ChatMessage {
	@Id String id;
	@Indexed UUID sender;
	@Indexed UUID recipient;
	String text;
	Date timestamp;
}
