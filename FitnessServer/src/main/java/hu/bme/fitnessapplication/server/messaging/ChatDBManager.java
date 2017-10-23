package hu.bme.fitnessapplication.server.messaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

public class ChatDBManager {

	private static final ChatDBManager INSTANCE = new ChatDBManager();
	private static Integer REDIS_DB = 1;
	private static String REDIS_HOST = "91.134.196.66";
	private Jedis jedis = null;
	private String currentUser;
	private String currentRoom = "main";

    private ChatDBManager() {

    }

    public static ChatDBManager getInstance() {
        return INSTANCE;
    }
    

    public void connect() {
        jedis = new Jedis(REDIS_HOST);
        jedis.connect();
        jedis.select(REDIS_DB);
    }

    public void disconnect() {
        if (jedis != null) {
            jedis.disconnect();
        }
    }
    

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void registerUser(String username) {
        jedis.set("online:" + username, "true");
        setCurrentUser(username);
    }

    public void unregisterCurrentUser() {
        jedis.del("online:" + getCurrentUser());
    }
    
    public List<String> getOnlineUsers() {
        ScanParams params = new ScanParams();
        params.match("online:*");

        //Scan creates a REDIS cursor based iterator, which starts at 0 and
        //terminates when the result is 0, see: https://redis.io/commands/scan
        ScanResult<String> scanResult = jedis.scan("0", params);
        String nextCursor = scanResult.getStringCursor();

        boolean scanEnd = false;
        List<String> users = new ArrayList<>();
        while (!scanEnd) {
            for (String key : scanResult.getResult()) {
                users.add(key.replaceFirst("online:", ""));
            }

            scanResult = jedis.scan(nextCursor, params);
            nextCursor = scanResult.getStringCursor();

            if (nextCursor.equals("0")) {
                scanEnd = true;
            }
        }
        return users;
    }
    
    public void sendMessage(String message) {
        String msg = (new Date()).getTime() + ":" + getCurrentUser() + ":" + message;
        jedis.rpush(getCurrentRoom() + ":messages", msg);
    }

    public List<String> getMessages() {
        return jedis.lrange(getCurrentRoom() + ":messages", 0, -1);
    }
    
    public void addRoom(String roomName) {
        jedis.rpush("rooms", roomName);
    }

    public List<String> getRooms() {
        return jedis.lrange("rooms", 0, -1);
    }


    public void setCurrentRoom(String currentRoom) {
        this.currentRoom = currentRoom;
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public Map<String,Long> getRoomsLength(){
    	//TODO: implement
            return Collections.emptyMap();
    }
}
