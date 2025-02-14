import java.util.HashMap;
import java.util.Map;

abstract class SmartDevice {
    protected String name;
    protected boolean powerStatus;

    public SmartDevice(String name) {
        this.name = name;
        this.powerStatus = false;
    }

    public void togglePower() {
        powerStatus = !powerStatus;
        System.out.println(name + " is now " + (powerStatus ? "ON" : "OFF"));
    }

    public abstract void performAction();
}

class SmartLight extends SmartDevice {
    public SmartLight(String name) {
        super(name);
    }

    @Override
    public void performAction() {
        System.out.println(name + " is " + (powerStatus ? "illuminating the room." : "turned off."));
    }
}

class SmartThermostat extends SmartDevice {
    private int temperature;

    public SmartThermostat(String name, int initialTemperature) {
        super(name);
        this.temperature = initialTemperature;
    }

    @Override
    public void performAction() {
        System.out.println(name + " is " + (powerStatus ? "heating to " + temperature + " degrees." : "turned off."));
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
        System.out.println(name + " temperature set to " + temperature + " degrees.");
    }
}

class SmartHome {
    private Map<String, SmartDevice> devices;

    public SmartHome() {
        devices = new HashMap<>();
    }

    public void addDevice(SmartDevice device) {
        devices.put(device.name, device);
        System.out.println(device.name + " added to the home.");
    }

    public void toggleDevicePower(String deviceName) {
        SmartDevice device = devices.get(deviceName);
        if (device != null) {
            device.togglePower();
        } else {
            System.out.println("No such device found: " + deviceName);
        }
    }
    
    public void performDeviceAction(String deviceName) {
        SmartDevice device = devices.get(deviceName);
        if (device != null) {
            device.performAction();
        } else {
            System.out.println("No such device found: " + deviceName);
        }
    }

    public static void main(String[] args) {
        SmartHome home = new SmartHome();

        SmartLight livingRoomLight = new SmartLight("Living Room Light");
        SmartThermostat thermostat = new SmartThermostat("Thermostat", 22);

        home.addDevice(livingRoomLight);
        home.addDevice(thermostat);

        home.toggleDevicePower("Living Room Light");
        home.performDeviceAction("Living Room Light");

        home.toggleDevicePower("Thermostat");
        thermostat.setTemperature(24);
        home.performDeviceAction("Thermostat");
    }
}