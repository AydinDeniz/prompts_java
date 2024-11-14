
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class HttpServer {
    private final int port;
    private final ExecutorService threadPool;

    public HttpServer(int port, int threadPoolSize) {
        this.port = port;
        this.threadPool = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                threadPool.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HttpServer server = new HttpServer(8080, 10); // port 8080, 10 threads
        server.start();
    }
}

class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            
            String line = in.readLine();
            if (line == null) return;
            
            String[] requestLine = line.split(" ");
            String method = requestLine[0];
            String path = requestLine[1];
            
            System.out.println("Received " + method + " request for " + path);
            
            if (method.equals("GET")) {
                handleGetRequest(out, path);
            } else if (method.equals("POST")) {
                handlePostRequest(in, out);
            } else {
                sendErrorResponse(out, "405 Method Not Allowed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleGetRequest(PrintWriter out, String path) {
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/plain");
        out.println();
        out.println("You sent a GET request for " + path);
    }

    private void handlePostRequest(BufferedReader in, PrintWriter out) throws IOException {
        StringBuilder payload = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null && !line.isEmpty()) {
            payload.append(line).append("\n");
        }
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/plain");
        out.println();
        out.println("You sent a POST request with payload:\n" + payload.toString());
    }

    private void sendErrorResponse(PrintWriter out, String status) {
        out.println("HTTP/1.1 " + status);
        out.println("Content-Type: text/plain");
        out.println();
        out.println(status);
    }
}
