// TrafficLight.java
public class TrafficLight {
    private String location;
    private boolean isGreen;

    public TrafficLight(String location) {
        this.location = location;
        this.isGreen = true; // Default to green light
    }

    public void toggleLight() {
        isGreen = !isGreen;
    }

    public String getStatus() {
        return isGreen ? "GREEN" : "RED";
    }

    @Override
    public String toString() {
        return "TrafficLight[Location: " + location + ", Status: " + getStatus() + "]";
    }
}

// EnergyMeter.java
public class EnergyMeter {
    private String buildingName;
    private double energyConsumption;

    public EnergyMeter(String buildingName) {
        this.buildingName = buildingName;
        this.energyConsumption = Math.random() * 100; // Random initial consumption
    }

    public void updateConsumption() {
        energyConsumption += Math.random() * 10 - 5; // Random fluctuation
    }

    public double getEnergyConsumption() {
        return energyConsumption;
    }

    @Override
    public String toString() {
        return "EnergyMeter[Building: " + buildingName + ", Energy Consumption: " + energyConsumption + " kWh]";
    }
}

// PublicService.java
public class PublicService {
    private String serviceName;
    private boolean isOperational;

    public PublicService(String serviceName) {
        this.serviceName = serviceName;
        this.isOperational = true;
    }

    public void toggleService() {
        isOperational = !isOperational;
    }

    public String getStatus() {
        return isOperational ? "Operational" : "Not Operational";
    }

    @Override
    public String toString() {
        return "PublicService[Service: " + serviceName + ", Status: " + getStatus() + "]";
    }
}

// SmartCitySimulation.java
import java.util.ArrayList;
import java.util.List;

public class SmartCitySimulation {
    private List<TrafficLight> trafficLights;
    private List<EnergyMeter> energyMeters;
    private List<PublicService> publicServices;

    public SmartCitySimulation() {
        trafficLights = new ArrayList<>();
        energyMeters = new ArrayList<>();
        publicServices = new ArrayList<>();
    }

    public void addTrafficLight(TrafficLight light) {
        trafficLights.add(light);
    }

    public void addEnergyMeter(EnergyMeter meter) {
        energyMeters.add(meter);
    }

    public void addPublicService(PublicService service) {
        publicServices.add(service);
    }

    public void simulateCycle() {
        System.out.println("\nSimulating city infrastructure cycle...");

        for (TrafficLight light : trafficLights) {
            light.toggleLight();
            System.out.println(light);
        }

        for (EnergyMeter meter : energyMeters) {
            meter.updateConsumption();
            System.out.println(meter);
        }

        for (PublicService service : publicServices) {
            if (Math.random() > 0.8) service.toggleService(); // Randomly toggle service status
            System.out.println(service);
        }
    }
}

// SmartCityApp.java
public class SmartCityApp {
    public static void main(String[] args) {
        SmartCitySimulation simulation = new SmartCitySimulation();

        simulation.addTrafficLight(new TrafficLight("Main St & 1st Ave"));
        simulation.addTrafficLight(new TrafficLight("2nd St & 3rd Ave"));

        simulation.addEnergyMeter(new EnergyMeter("City Hall"));
        simulation.addEnergyMeter(new EnergyMeter("Public Library"));

        simulation.addPublicService(new PublicService("Fire Department"));
        simulation.addPublicService(new PublicService("Police Department"));

        for (int i = 0; i < 5; i++) {
            simulation.simulateCycle();
            try {
                Thread.sleep(2000); // Pause for 2 seconds between cycles
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}