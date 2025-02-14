// Vehicle.java
import java.util.Random;

public class Vehicle {
    private String id;
    private Position position;
    private Direction direction;

    public Vehicle(String id, Position startPosition, Direction direction) {
        this.id = id;
        this.position = startPosition;
        this.direction = direction;
    }

    public String getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    public void move() {
        switch (direction) {
            case NORTH:
                position.setY(position.getY() - 1);
                break;
            case SOUTH:
                position.setY(position.getY() + 1);
                break;
            case EAST:
                position.setX(position.getX() + 1);
                break;
            case WEST:
                position.setX(position.getX() - 1);
                break;
        }
    }

    public void changeDirection(Direction newDirection) {
        direction = newDirection;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "Vehicle " + id + " at " + position + " heading " + direction;
    }
}

// Direction.java
public enum Direction {
    NORTH, SOUTH, EAST, WEST;
}

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

    @Override
    public String toString() {
        return "Position(" + x + ", " + y + ")";
    }
}

// TrafficLight.java
public class TrafficLight {
    private Position position;
    private boolean greenLight;

    public TrafficLight(Position position) {
        this.position = position;
        this.greenLight = true;  // starts with green light
    }

    public void toggleLight() {
        greenLight = !greenLight;
    }

    public boolean isGreenLight() {
        return greenLight;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "TrafficLight at " + position + " is " + (greenLight ? "GREEN" : "RED");
    }
}

// TrafficSystem.java
import java.util.*;

public class TrafficSystem {
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<TrafficLight> lights = new ArrayList<>();
    private int time = 0;
    private Random random = new Random();

    public TrafficSystem() {
        // Initialize some vehicles and traffic lights
        vehicles.add(new Vehicle("V1", new Position(0, 0), Direction.EAST));
        vehicles.add(new Vehicle("V2", new Position(5, 5), Direction.WEST));
        lights.add(new TrafficLight(new Position(2, 0)));
        lights.add(new TrafficLight(new Position(4, 5)));
    }

    public void simulate() {
        for (int step = 0; step < 10; step++) {
            System.out.println("Time: " + time);
            for (TrafficLight light : lights) {
                light.toggleLight();
                System.out.println(light);
            }

            for (Vehicle vehicle : vehicles) {
                if (shouldStop(vehicle)) {
                    System.out.println(vehicle + " stopped at red light");
                } else {
                    vehicle.move();
                    randomRouteChanger(vehicle);
                    System.out.println(vehicle);
                }
            }
            System.out.println();
            time++;
        }
    }

    private boolean shouldStop(Vehicle vehicle) {
        for (TrafficLight light : lights) {
            if (vehicle.getPosition().equals(light.getPosition()) && !light.isGreenLight()) {
                return true;
            }
        }
        return false;
    }

    private void randomRouteChanger(Vehicle vehicle) {
        if (random.nextBoolean()) {
            vehicle.changeDirection(Direction.values()[random.nextInt(Direction.values().length)]);
        }
    }
}

// TrafficManagementSimulation.java
public class TrafficManagementSimulation {
    public static void main(String[] args) {
        TrafficSystem system = new TrafficSystem();
        system.simulate();
    }
}