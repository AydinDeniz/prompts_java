
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/chat")
public class ChatServer {
    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session) {
        clients.add(session);
        broadcast("User has joined the chat!", session);
        System.out.println("New connection: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        broadcast(message, session);
        System.out.println("Received message: " + message);
    }

    @OnClose
    public void onClose(Session session) {
        clients.remove(session);
        broadcast("User has left the chat.", session);
        System.out.println("Connection closed: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Error in session " + session.getId() + ": " + throwable.getMessage());
    }

    private void broadcast(String message, Session sender) {
        synchronized (clients) {
            for (Session client : clients) {
                if (client.isOpen() && !client.equals(sender)) {
                    try {
                        client.getBasicRemote().sendText("User " + sender.getId() + ": " + message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Chat server started. Deploy this in a WebSocket container like Tomcat or Jetty.");
    }
}
