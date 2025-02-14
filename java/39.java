// Sensor.java
public class Sensor {
    private String id;
    private String type;
    private double currentReading;

    public Sensor(String id, String type) {
        this.id = id;
        this.type = type;
        this.currentReading = 0.0;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getCurrentReading() {
        return currentReading;
    }

    public void setCurrentReading(double newReading) {
        this.currentReading = newReading;
    }

    @Override
    public String toString() {
        return "Sensor[" + id + ", " + type + ", Reading: " + currentReading + "]";
    }
}

// EnergyMonitor.java
import java.util.ArrayList;
import java.util.List;

public class EnergyMonitor {
    private List<Sensor> sensors;

    public EnergyMonitor() {
        this.sensors = new ArrayList<>();
    }

    public void addSensor(Sensor sensor) {
        sensors.add(sensor);
    }

    public double getTotalEnergyConsumption() {
        return sensors.stream().mapToDouble(Sensor::getCurrentReading).sum();
    }

    public void displaySensorReadings() {
        sensors.forEach(System.out::println);
    }
}

// EnergyOptimizer.java
public class EnergyOptimizer {
    private double threshold;

    public EnergyOptimizer(double threshold) {
        this.threshold = threshold;
    }

    public void optimizeEnergyConsumption(EnergyMonitor monitor) {
        double totalConsumption = monitor.getTotalEnergyConsumption();
        System.out.println("Total Energy Consumption: " + totalConsumption);

        if (totalConsumption > threshold) {
            System.out.println("Recommendation: Reduce usage of high-consumption devices.");
        } else {
            System.out.println("Energy consumption is within the optimal range.");
        }
    }
}

// TrendPredictor.java
import java.util.Random;

public class TrendPredictor {
    private Random random = new Random();

    public String predictTrend() {
        int trend = random.nextInt(3);
        switch (trend) {
            case 0:
                return "Consumption expected to increase.";
            case 1:
                return "Consumption expected to decrease.";
            default:
                return "Consumption expected to remain stable.";
        }
    }
}

// SmartBuildingApp.java
public class SmartBuildingApp {
    public static void main(String[] args) {
        Sensor tempSensor = new Sensor("T1", "Temperature");
        Sensor lightSensor = new Sensor("L1", "Light");
        Sensor motionSensor = new Sensor("M1", "Motion");

        tempSensor.setCurrentReading(150.0); // Simulated reading
        lightSensor.setCurrentReading(200.0); // Simulated reading
        motionSensor.setCurrentReading(50.0); // Simulated reading

        EnergyMonitor monitor = new EnergyMonitor();
        monitor.addSensor(tempSensor);
        monitor.addSensor(lightSensor);
        monitor.addSensor(motionSensor);

        monitor.displaySensorReadings();

        EnergyOptimizer optimizer = new EnergyOptimizer(300.0);
        optimizer.optimizeEnergyConsumption(monitor);

        TrendPredictor predictor = new TrendPredictor();
        System.out.println("Energy Trend Prediction: " + predictor.predictTrend());
    }
}