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

    public Position move(Direction direction) {
        switch (direction) {
            case NORTH: return new Position(x, y - 1);
            case SOUTH: return new Position(x, y + 1);
            case EAST: return new Position(x + 1, y);
            case WEST: return new Position(x - 1, y);
            default: throw new IllegalStateException("Unexpected direction: " + direction);
        }
    }

    @Override
    public String toString() {
        return "Position(" + x + ", " + y + ")";
    }
}

// Vehicle.java
public class Vehicle {
    private Position position;
    private Direction direction;

    public Vehicle(Position position, Direction direction) {
        this.position = position;
        this.direction = direction;
    }

    public Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public void move() {
        position = position.move(direction);
    }

    public void turnLeft() {
        switch (direction) {
            case NORTH: direction = Direction.WEST; break;
            case SOUTH: direction = Direction.EAST; break;
            case EAST: direction = Direction.NORTH; break;
            case WEST: direction = Direction.SOUTH; break;
        }
    }

    public void turnRight() {
        switch (direction) {
            case NORTH: direction = Direction.EAST; break;
            case SOUTH: direction = Direction.WEST; break;
            case EAST: direction = Direction.SOUTH; break;
            case WEST: direction = Direction.NORTH; break;
        }
    }

    @Override
    public String toString() {
        return "Vehicle at " + position + " facing " + direction;
    }
}

// Obstacle.java
public class Obstacle {
    private Position position;

    public Obstacle(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Obstacle at " + position;
    }
}

// SimulationEnvironment.java
import java.util.ArrayList;
import java.util.List;

public class SimulationEnvironment {
    private List<Obstacle> obstacles;
    private Vehicle vehicle;

    public SimulationEnvironment(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.obstacles = new ArrayList<>();
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }

    public void simulateStep() {
        System.out.println("Simulating step...");
        vehicle.move();
        System.out.println(vehicle);

        for (Obstacle obstacle : obstacles) {
            if (vehicle.getPosition().getX() == obstacle.getPosition().getX() && 
                vehicle.getPosition().getY() == obstacle.getPosition().getY()) {
                System.out.println("Warning: Obstacle detected at " + obstacle.getPosition());
                vehicle.turnRight(); // Simple reaction to obstacle
            }
        }
    }

    public void runSimulation(int steps) {
        for (int i = 0; i < steps; i++) {
            simulateStep();
        }
    }
}

// AutonomousVehicleSimulationApp.java
public class AutonomousVehicleSimulationApp {
    public static void main(String[] args) {
        Vehicle vehicle = new Vehicle(new Position(0, 0), Direction.NORTH);
        SimulationEnvironment environment = new SimulationEnvironment(vehicle);

        environment.addObstacle(new Obstacle(new Position(0, -2)));
        environment.addObstacle(new Obstacle(new Position(2, 2)));

        System.out.println("Starting Autonomous Vehicle Simulation...");
        environment.runSimulation(10);
    }
}