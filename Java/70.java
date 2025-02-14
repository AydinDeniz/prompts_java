// SensorData.java
public class SensorData {
    private String sensorId;
    private double value; // e.g., moisture level, temperature
    private String type; // e.g., "Moisture", "Temperature"

    public SensorData(String sensorId, double value, String type) {
        this.sensorId = sensorId;
        this.value = value;
        this.type = type;
    }

    public String getSensorId() {
        return sensorId;
    }

    public double getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "SensorData[ID: " + sensorId + ", Type: " + type + ", Value: " + value + "]";
    }
}

// Actuator.java
public class Actuator {
    private String actuatorId;
    private String type; // e.g., "Irrigation", "Cooling"

    public Actuator(String actuatorId, String type) {
        this.actuatorId = actuatorId;
        this.type = type;
    }

    public void activate() {
        System.out.println(type + " Actuator " + actuatorId + " activated.");
    }

    public void deactivate() {
        System.out.println(type + " Actuator " + actuatorId + " deactivated.");
    }
}

// IoTSensor.java
import java.util.Random;

public class IoTSensor {
    private String sensorId;
    private String type;
    private Random random;

    public IoTSensor(String sensorId, String type) {
        this.sensorId = sensorId;
        this.type = type;
        this.random = new Random();
    }

    public SensorData readSensorData() {
        double value = type.equals("Moisture") ? random.nextDouble() * 100 : 20 + random.nextDouble() * 15;
        SensorData data = new SensorData(sensorId, value, type);
        System.out.println("Reading from " + sensorId + ": " + data);
        return data;
    }
}

// SmartAgricultureSystem.java
import java.util.ArrayList;
import java.util.List;

public class SmartAgricultureSystem {
    private List<IoTSensor> sensors;
    private List<Actuator> actuators;

    public SmartAgricultureSystem() {
        this.sensors = new ArrayList<>();
        this.actuators = new ArrayList<>();
    }

    public void addSensor(IoTSensor sensor) {
        sensors.add(sensor);
    }

    public void addActuator(Actuator actuator) {
        actuators.add(actuator);
    }

    public void monitorAndActuate() {
        for (IoTSensor sensor : sensors) {
            SensorData data = sensor.readSensorData();
            if ("Moisture".equals(data.getType()) && data.getValue() < 30.0) {
                activateActuator("Irrigation");
            } else if ("Temperature".equals(data.getType()) && data.getValue() > 30.0) {
                activateActuator("Cooling");
            }
        }
    }

    private void activateActuator(String type) {
        for (Actuator actuator : actuators) {
            if (actuator.getType().equals(type)) {
                actuator.activate();
            }
        }
    }

    public void deactivateAllActuators() {
        for (Actuator actuator : actuators) {
            actuator.deactivate();
        }
    }
}

// SmartAgricultureApp.java
public class SmartAgricultureApp {
    public static void main(String[] args) {
        SmartAgricultureSystem agricultureSystem = new SmartAgricultureSystem();

        IoTSensor moistureSensor = new IoTSensor("MoistureSensor1", "Moisture");
        IoTSensor temperatureSensor = new IoTSensor("TemperatureSensor1", "Temperature");
        agricultureSystem.addSensor(moistureSensor);
        agricultureSystem.addSensor(temperatureSensor);

        Actuator irrigationActuator = new Actuator("Irrigation1", "Irrigation");
        Actuator coolingActuator = new Actuator("Cooling1", "Cooling");
        agricultureSystem.addActuator(irrigationActuator);
        agricultureSystem.addActuator(coolingActuator);

        agricultureSystem.monitorAndActuate();
        agricultureSystem.deactivateAllActuators();
    }
