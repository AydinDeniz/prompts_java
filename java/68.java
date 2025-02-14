// User.java
public class User {
    private String username;
    private String avatar;

    public User(String username, String avatar) {
        this.username = username;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    @Override
    public String toString() {
        return "User[Username: " + username + ", Avatar: " + avatar + "]";
    }
}

// VirtualRoom.java
import java.util.ArrayList;
import java.util.List;

public class VirtualRoom {
    private String roomName;
    private List<User> users;

    public VirtualRoom(String roomName) {
        this.roomName = roomName;
        this.users = new ArrayList<>();
    }

    public String getRoomName() {
        return roomName;
    }

    public void addUser(User user) {
        users.add(user);
        System.out.println(user.getUsername() + " joined the room.");
    }

    public void removeUser(User user) {
        users.remove(user);
        System.out.println(user.getUsername() + " left the room.");
    }

    public void displayUsers() {
        System.out.println("Users in " + roomName + ":");
        for (User user : users) {
            System.out.println(user);
        }
    }
}

// VRObject.java
public class VRObject {
    private String name;
    private String type;
    
    public VRObject(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "VRObject[Name: " + name + ", Type: " + type + "]";
    }
}

// CollaborationSession.java
import java.util.ArrayList;
import java.util.List;

public class CollaborationSession {
    private VirtualRoom room;
    private List<VRObject> vrObjects;

    public CollaborationSession(VirtualRoom room) {
        this.room = room;
        this.vrObjects = new ArrayList<>();
    }

    public void addVRObject(VRObject vrObject) {
        vrObjects.add(vrObject);
        System.out.println(vrObject.getName() + " added to the collaboration.");
    }

    public void performCollaborativeActivity() {
        System.out.println("Performing collaborative activities in " + room.getRoomName() + "...");
        for (VRObject vrObject : vrObjects) {
            System.out.println("Interacting with " + vrObject);
        }
        System.out.println("Collaboration session complete.");
    }
}

// CollaborationApp.java
public class CollaborationApp {
    public static void main(String[] args) {
        VirtualRoom room = new VirtualRoom("Design Lab");
        User alice = new User("Alice", "Alien Avatar");
        User bob = new User("Bob", "Robot Avatar");

        room.addUser(alice);
        room.addUser(bob);
        room.displayUsers();

        CollaborationSession session = new CollaborationSession(room);

        VRObject table = new VRObject("Table", "Furniture");
        VRObject whiteboard = new VRObject("Whiteboard", "Tool");

        session.addVRObject(table);
        session.addVRObject(whiteboard);
        
        session.performCollaborativeActivity();

        room.removeUser(alice);
        room.displayUsers();
    }
}