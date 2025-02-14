import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

class PlayerHandler implements Runnable {
    private Socket socket;
    private String playerName;
    private PrintWriter out;

    public PlayerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            this.out = out;
            out.println("Welcome to the game server! Enter your name:");
            playerName = in.readLine();
            out.println("Hello, " + playerName + "! You have joined the game.");

            String message;
            while ((message = in.readLine()) != null) {
                GameServer.broadcast(playerName + ": " + message, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}

public class GameServer {
    private static final int PORT = 12345;
    private static Set<PlayerHandler> playerHandlers = ConcurrentHashMap.newKeySet();

    public static void main(String[] args) {
        System.out.println("Game server started...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                PlayerHandler playerHandler = new PlayerHandler(socket);
                playerHandlers.add(playerHandler);
                new Thread(playerHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(String message, PlayerHandler excludePlayer) {
        for (PlayerHandler playerHandler : playerHandlers) {
            if (playerHandler != excludePlayer) {
                playerHandler.sendMessage(message);
            }
        }
    }
}