// NetworkPacket.java
public class NetworkPacket {
    private String sourceIP;
    private String destinationIP;
    private int size;

    public NetworkPacket(String sourceIP, String destinationIP, int size) {
        this.sourceIP = sourceIP;
        this.destinationIP = destinationIP;
        this.size = size;
    }

    public String getSourceIP() {
        return sourceIP;
    }

    public String getDestinationIP() {
        return destinationIP;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Packet[Source: " + sourceIP + ", Destination: " + destinationIP + ", Size: " + size + "]";
    }
}

// NetworkMonitor.java
import java.util.ArrayList;
import java.util.List;

public class NetworkMonitor {
    private List<NetworkPacket> packetLog = new ArrayList<>();

    public void logPacket(NetworkPacket packet) {
        packetLog.add(packet);
        System.out.println("Logged " + packet);
    }

    public List<NetworkPacket> getPackets() {
        return packetLog;
    }

    public void clearLog() {
        packetLog.clear();
    }
}

// IntrusionDetector.java
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntrusionDetector {
    private NetworkMonitor networkMonitor;

    public IntrusionDetector(NetworkMonitor networkMonitor) {
        this.networkMonitor = networkMonitor;
    }

    public void analyzeTraffic() {
        System.out.println("Analyzing network traffic...");
        List<NetworkPacket> packets = networkMonitor.getPackets();
        Map<String, Integer> ipTraffic = new HashMap<>();

        for (NetworkPacket packet : packets) {
            ipTraffic.put(packet.getSourceIP(), ipTraffic.getOrDefault(packet.getSourceIP(), 0) + packet.getSize());
        }

        for (Map.Entry<String, Integer> entry : ipTraffic.entrySet()) {
            if (entry.getValue() > 1000) { // Threshold for detecting an anomaly
                triggerAlert("High traffic from IP: " + entry.getKey());
            }
        }
    }

    private void triggerAlert(String message) {
        System.out.println("ALERT! " + message);
    }
}

// ResponseSystem.java
public class ResponseSystem {
    public void takeAction(String alertMessage) {
        System.out.println("Taking response action for: " + alertMessage);
        // Placeholder for response logic, e.g., blocking IP or notifying admin
    }
}

// CyberSecurityApp.java
import java.util.Random;

public class CyberSecurityApp {
    public static void main(String[] args) {
        NetworkMonitor monitor = new NetworkMonitor();
        IntrusionDetector detector = new IntrusionDetector(monitor);
        ResponseSystem response = new ResponseSystem();

        // Simulate some network traffic
        Random random = new Random();
        String[] ips = {"192.168.1.1", "192.168.1.2", "10.0.0.1"};

        for (int i = 0; i < 20; i++) {
            String sourceIP = ips[random.nextInt(ips.length)];
            String destinationIP = ips[random.nextInt(ips.length)];
            int size = random.nextInt(200) + 1; // Random packet size
            monitor.logPacket(new NetworkPacket(sourceIP, destinationIP, size));
        }

        // Analyze traffic and respond to potential threats
        detector.analyzeTraffic();
    }
}