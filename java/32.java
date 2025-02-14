// Position.java
public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean equals(Position other) {
        return this.x == other.getX() && this.y == other.getY();
    }

    @Override
    public String toString() {
        return "Position(" + x + ", " + y + ")";
    }
}

// Sensor.java
interface Sensor {
    boolean detectObstacle(Position position);
}

// SimpleProximitySensor.java
import java.util.Random;

public class SimpleProximitySensor implements Sensor {
    private Random random = new Random();

    @Override
    public boolean detectObstacle(Position position) {
        // Randomly simulate obstacle detection for demonstration
        return random.nextInt(10) < 2; // 20% chance of detecting an obstacle
    }
}

// Grid.java
public class Grid {
    private final int width;
    private final int height;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean isInBounds(Position position) {
        return position.getX() >= 0 && position.getX() < width &&
               position.getY() >= 0 && position.getY() < height;
    }
}

// Drone.java
import java.util.LinkedList;
import java.util.Queue;

public class Drone {
    private Position position;
    private Grid grid;
    private Sensor sensor;
    private Queue<Position> path;

    public Drone(Position startPosition, Grid grid, Sensor sensor) {
        this.position = startPosition;
        this.grid = grid;
        this.sensor = sensor;
        this.path = new LinkedList<>();
    }

    public void navigateTo(Position destination) {
        System.out.println("Navigating to " + destination);
        findPathTo(destination);
        followPath();
    }

    private void findPathTo(Position destination) {
        // Simple straight-line pathfinding demonstration
        int x = position.getX();
        int y = position.getY();
        path.clear();

        while (x != destination.getX() || y != destination.getY()) {
            if (x < destination.getX()) x++;
            else if (x > destination.getX()) x--;
            
            if (y < destination.getY()) y++;
            else if (y > destination.getY()) y--;

            Position next = new Position(x, y);
            if (grid.isInBounds(next) && !sensor.detectObstacle(next)) {
                path.add(next);
            }
        }
    }

    private void followPath() {
        while (!path.isEmpty()) {
            Position nextPosition = path.poll();
            if (sensor.detectObstacle(nextPosition)) {
                System.out.println("Obstacle detected at " + nextPosition + ". Re-routing...");
                break;
            }
            position = nextPosition;
            System.out.println("Moved to " + position);
        }
        System.out.println("Current position: " + position);
    }
}

// DroneSimulation.java
public class DroneSimulation {
    public static void main(String[] args) {
        Grid grid = new Grid(10, 10);
        Position startPosition = new Position(0, 0);
        Sensor sensor = new SimpleProximitySensor();

        Drone drone = new Drone(startPosition, grid, sensor);
        Position destination = new Position(5, 7);

        System.out.println("Starting drone navigation simulation...");
        drone.navigateTo(destination);
        System.out.println("Destination reached or stopped due to obstacle.");
    }
}