// WeatherData.java
public class WeatherData {
    private String location;
    private double temperature;
    private double humidity;
    private double windSpeed;
    private String description;

    public WeatherData(String location, double temperature, double humidity, double windSpeed, String description) {
        this.location = location;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "WeatherData[Location: " + location + ", Temp: " + temperature + "°C, Humidity: " + humidity + "%, Wind: " + windSpeed + " km/h, Desc: " + description + "]";
    }
}

// WeatherDataProvider.java
import java.util.*;

public class WeatherDataProvider {
    private static final List<WeatherData> SAMPLE_DATA = Arrays.asList(
        new WeatherData("New York", 22.5, 60.0, 15.0, "Clear sky"),
        new WeatherData("Los Angeles", 25.0, 50.0, 10.0, "Sunny"),
        new WeatherData("Chicago", 18.5, 70.0, 20.0, "Partly cloudy"),
        new WeatherData("Miami", 30.0, 80.0, 25.0, "Thunderstorms")
    );

    public WeatherData getCurrentWeather(String location) {
        return SAMPLE_DATA.stream()
            .filter(data -> data.getLocation().equalsIgnoreCase(location))
            .findFirst()
            .orElse(null);
    }

    public List<WeatherData> getAllWeatherData() {
        return SAMPLE_DATA;
    }
}

// WeatherAnalyzer.java
public class WeatherAnalyzer {

    public String predictWeather(WeatherData currentData) {
        double temp = currentData.getTemperature();
        double humidity = currentData.getHumidity();
        double wind = currentData.getWindSpeed();
        
        if (humidity > 75.0 && wind > 20.0) {
            return "High likelihood of rain or thunderstorms.";
        } else if (temp > 30.0) {
            return "Hot and dry conditions expected.";
        } else if (temp < 5.0) {
            return "Cold weather with potential snow or frost.";
        } else {
            return "Mild weather expected.";
        }
    }
}

// WeatherVisualization.java
public class WeatherVisualization {

    public void displayWeatherCharts(WeatherData data) {
        System.out.println("Generating temperature chart for " + data.getLocation() + "...");
        System.out.println("Temperature: " + data.getTemperature() + "°C");
        // Placeholder for actual chart generation
        System.out.println("Generating humidity chart...");
        System.out.println("Humidity: " + data.getHumidity() + "%");
        // Placeholder for actual chart generation
    }
}

// WeatherForecastApp.java
public class WeatherForecastApp {
    public static void main(String[] args) {
        WeatherDataProvider provider = new WeatherDataProvider();
        WeatherAnalyzer analyzer = new WeatherAnalyzer();
        WeatherVisualization visualization = new WeatherVisualization();

        String location = "New York";
        WeatherData currentWeather = provider.getCurrentWeather(location);

        if (currentWeather != null) {
            System.out.println("Current Weather: " + currentWeather);
            String prediction = analyzer.predictWeather(currentWeather);
            System.out.println("Weather Prediction: " + prediction);

            visualization.displayWeatherCharts(currentWeather);
        } else {
            System.out.println("Weather data not available for location: " + location);
        }
    }
}