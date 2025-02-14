import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class User {
    private String name;
    private Set<User> friends;

    public User(String name) {
        this.name = name;
        this.friends = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void addFriend(User friend) {
        friends.add(friend);
    }

    public Set<User> getFriends() {
        return friends;
    }

    public int getNumberOfFriends() {
        return friends.size();
    }
}

class SocialNetwork {
    private Map<String, User> users;

    public SocialNetwork() {
        users = new HashMap<>();
    }

    public void addUser(String name) {
        if (!users.containsKey(name)) {
            users.put(name, new User(name));
        } else {
            System.out.println("User already exists!");
        }
    }

    public void addFriendship(String name1, String name2) {
        User user1 = users.get(name1);
        User user2 = users.get(name2);

        if (user1 != null && user2 != null) {
            user1.addFriend(user2);
            user2.addFriend(user1);
        } else {
            System.out.println("One or both users not found!");
        }
    }

    public int degreeOfCentrality(String name) {
        User user = users.get(name);
        if (user != null) {
            return user.getNumberOfFriends();
        }
        return -1;
    }

    public List<String> getUsersWithMostConnections() {
        int maxConnections = 0;
        List<String> mostConnectedUsers = new ArrayList<>();

        for (User user : users.values()) {
            int connections = user.getNumberOfFriends();
            if (connections > maxConnections) {
                maxConnections = connections;
                mostConnectedUsers.clear();
                mostConnectedUsers.add(user.getName());
            } else if (connections == maxConnections) {
                mostConnectedUsers.add(user.getName());
            }
        }
        return mostConnectedUsers;
    }

    public void printNetwork() {
        for (User user : users.values()) {
            System.out.println(user.getName() + ": " + user.getNumberOfFriends() + " friends");
        }
    }

    public static void main(String[] args) {
        SocialNetwork network = new SocialNetwork();
        network.addUser("Alice");
        network.addUser("Bob");
        network.addUser("Charlie");
        network.addUser("David");

        network.addFriendship("Alice", "Bob");
        network.addFriendship("Alice", "Charlie");
        network.addFriendship("Bob", "David");
        network.addFriendship("Charlie", "David");
        
        network.printNetwork();

        System.out.println("Degree of Centrality for Alice: " + network.degreeOfCentrality("Alice"));
        System.out.println("Users with most connections: " + network.getUsersWithMostConnections());
    }
}