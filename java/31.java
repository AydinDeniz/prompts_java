// WebSocketServer.java
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/editor")
public class WebSocketServer {
    private static Set<Session> userSessions = 
        Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());
    private static Document document = new Document("");

    @OnOpen
    public void handleOpen(Session userSession) {
        userSessions.add(userSession);
        System.out.println("New user connected: " + userSession.getId());
        broadcastDocument();
    }

    @OnMessage
    public void handleMessage(String message, Session userSession) {
        System.out.println("Message from " + userSession.getId() + ": " + message);
        applyEdit(message);
        broadcastDocument();
    }

    @OnClose
    public void handleClose(Session userSession) {
        userSessions.remove(userSession);
        System.out.println("User disconnected: " + userSession.getId());
    }

    @OnError
    public void handleError(Throwable t) {
        t.printStackTrace();
    }

    private void applyEdit(String edit) {
        document.append(edit);
    }

    private void broadcastDocument() {
        String docContent = document.getContent();
        for (Session session : userSessions) {
            try {
                session.getBasicRemote().sendText(docContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

// Document.java
import java.util.ArrayList;
import java.util.List;

class Document {
    private StringBuilder content;

    public Document(String initialContent) {
        this.content = new StringBuilder(initialContent);
    }

    public synchronized void append(String newText) {
        content.append(newText);
    }

    public synchronized String getContent() {
        return content.toString();
    }
}

// index.html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Collaborative Document Editor</title>
</head>
<body>
    <h1>Real-Time Collaborative Document Editor</h1>
    <textarea id="editor" rows="20" cols="100"></textarea>
    <script>
        const editor = document.getElementById('editor');
        const socket = new WebSocket('ws://localhost:8080/editor');

        socket.onmessage = (event) => {
            editor.value = event.data;
        };

        editor.oninput = () => {
            socket.send(editor.value);
        };
    </script>
</body>
</html>

// WebSocketServerMain.java
import org.glassfish.tyrus.server.Server;

public class WebSocketServerMain {
    public static void main(String[] args) {
        Server server = new Server("localhost", 8080, "/", WebSocketServer.class);

        try {
            server.start();
            System.out.println("Server started. Listening on ws://localhost:8080/editor");
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}