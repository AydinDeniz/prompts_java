// ARLocation.java
public class ARLocation {
    private double latitude;
    private double longitude;
    private double altitude;

    public ARLocation(double latitude, double longitude, double altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    @Override
    public String toString() {
        return "ARLocation[lat=" + latitude + ", lon=" + longitude + ", alt=" + altitude + "]";
    }
}

// ARPath.java
import java.util.ArrayList;
import java.util.List;

public class ARPath {
    private List<ARLocation> waypoints;

    public ARPath() {
        this.waypoints = new ArrayList<>();
    }

    public void addWaypoint(ARLocation location) {
        waypoints.add(location);
    }

    public List<ARLocation> getWaypoints() {
        return waypoints;
    }

    @Override
    public String toString() {
        return "ARPath[Waypoints=" + waypoints + "]";
    }
}

// SensorData.java
public class SensorData {
    private double accelerometer;
    private double gyroscope;

    public SensorData(double accelerometer, double gyroscope) {
        this.accelerometer = accelerometer;
        this.gyroscope = gyroscope;
    }

    public double getAccelerometer() {
        return accelerometer;
    }

    public double getGyroscope() {
        return gyroscope;
    }

    @Override
    public String toString() {
        return "SensorData[Accel=" + accelerometer + ", Gyro=" + gyroscope + "]";
    }
}

// ARNavigationSystem.java
import java.util.*;

public class ARNavigationSystem {
    private ARPath path;
    private SensorData sensorData;

    public ARNavigationSystem(ARPath path) {
        this.path = path;
    }

    public void updateSensorData(SensorData data) {
        this.sensorData = data;
        System.out.println("Sensor data updated: " + data);
    }

    public void displayCurrentPath() {
        System.out.println("Current Path: " + path);
    }

    public ARLocation getNextWaypoint() {
        List<ARLocation> waypoints = path.getWaypoints();
        if (!waypoints.isEmpty()) {
            return waypoints.get(0); // Simulate getting the next waypoint
        }
        return null;
    }

    public void navigate() {
        ARLocation nextWaypoint = getNextWaypoint();
        while(nextWaypoint != null) {
            System.out.println("Navigating to: " + nextWaypoint);
            // Simulate moving to next waypoint and updating path
            path.getWaypoints().remove(0);
            nextWaypoint = getNextWaypoint();
        }
        System.out.println("Navigation complete.");
    }
}

// ARNavigationApp.java
public class ARNavigationApp {
    public static void main(String[] args) {
        ARPath path = new ARPath();
        path.addWaypoint(new ARLocation(37.7749, -122.4194, 0)); // Example location
        path.addWaypoint(new ARLocation(37.7750, -122.4184, 0));
        path.addWaypoint(new ARLocation(37.7751, -122.4174, 0));

        ARNavigationSystem navigationSystem = new ARNavigationSystem(path);

        navigationSystem.displayCurrentPath();

        SensorData sensorData = new SensorData(0.98, 0.05);
        navigationSystem.updateSensorData(sensorData);

        navigationSystem.navigate();
    }
}