// Message.java
import java.time.LocalDateTime;

public class Message {
    private String sender;
    private String content;
    private LocalDateTime timestamp;

    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + sender + ": " + content;
    }
}

// Peer.java
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Peer {
    private String username;
    private ServerSocket serverSocket;
    private ConcurrentMap<String, Socket> peerConnections;

    public Peer(String username, int port) throws IOException {
        this.username = username;
        this.serverSocket = new ServerSocket(port);
        this.peerConnections = new ConcurrentHashMap<>();
        listenForConnections();
    }

    public void connectToPeer(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        new Thread(new PeerHandler(socket)).start();
        peerConnections.put(socket.getRemoteSocketAddress().toString(), socket);
        sendMessage(socket, new Message(username, "Hello from " + username));
    }

    private void listenForConnections() {
        new Thread(() -> {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Connected to " + clientSocket.getRemoteSocketAddress());
                    new Thread(new PeerHandler(clientSocket)).start();
                    peerConnections.put(clientSocket.getRemoteSocketAddress().toString(), clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendMessage(Socket socket, Message message) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(message);
    }

    public void broadcastMessage(Message message) {
        peerConnections.values().forEach(socket -> {
            try {
                sendMessage(socket, message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    class PeerHandler implements Runnable {
        private Socket socket;

        PeerHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String msg;
                while ((msg = in.readLine()) != null) {
                    System.out.println(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                peerConnections.remove(socket.getRemoteSocketAddress().toString());
            }
        }
    }
}

// PeerToPeerChatApp.java
import java.io.IOException;
import java.util.Scanner;

public class PeerToPeerChatApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter port to listen on: ");
            int port = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Peer peer = new Peer(username, port);

            System.out.print("Enter peer host (or none): ");
            String host = scanner.nextLine();
            if (!host.equals("none")) {
                System.out.print("Enter peer port: ");
                int peerPort = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                peer.connectToPeer(host, peerPort);
            }

            while (true) {
                System.out.print("Enter message (exit to quit): ");
                String content = scanner.nextLine();
                if (content.equalsIgnoreCase("exit")) break;
                peer.broadcastMessage(new Message(username, content));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}