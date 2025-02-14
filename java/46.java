// DisasterEvent.java
import java.time.LocalDate;

public class DisasterEvent {
    private String type;
    private LocalDate date;
    private double severity;

    public DisasterEvent(String type, LocalDate date, double severity) {
        this.type = type;
        this.date = date;
        this.severity = severity;
    }

    public String getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getSeverity() {
        return severity;
    }

    @Override
    public String toString() {
        return "DisasterEvent[" + type + ", Date: " + date + ", Severity: " + severity + "]";
    }
}

// HistoricalDataAnalyzer.java
import java.util.*;
import java.time.*;

public class HistoricalDataAnalyzer {
    private List<DisasterEvent> events;
    private Random random;

    public HistoricalDataAnalyzer() {
        this.events = new ArrayList<>();
        this.random = new Random();
    }

    public void addEvent(DisasterEvent event) {
        events.add(event);
    }

    public double calculateAverageSeverity(String type) {
        return events.stream()
                .filter(e -> e.getType().equals(type))
                .mapToDouble(DisasterEvent::getSeverity)
                .average()
                .orElse(0.0);
    }

    public List<DisasterEvent> getEventsOfType(String type) {
        List<DisasterEvent> filteredEvents = new ArrayList<>();
        for (DisasterEvent event : events) {
            if (event.getType().equals(type)) {
                filteredEvents.add(event);
            }
        }
        return filteredEvents;
    }
}

// DisasterPredictor.java
public class DisasterPredictor {
    private HistoricalDataAnalyzer analyzer;

    public DisasterPredictor(HistoricalDataAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    public String predict(String disasterType) {
        List<DisasterEvent> pastEvents = analyzer.getEventsOfType(disasterType);
        if (pastEvents.isEmpty()) {
            return "Insufficient data for prediction.";
        }

        double averageSeverity = analyzer.calculateAverageSeverity(disasterType);
        if (averageSeverity > 6.0) {
            return "High likelihood of severe " + disasterType + " event.";
        } else if (averageSeverity > 3.0) {
            return "Moderate likelihood of " + disasterType + " event.";
        } else {
            return "Low likelihood of " + disasterType + " event.";
        }
    }
}

// NaturalDisasterPredictionApp.java
import java.time.LocalDate;

public class NaturalDisasterPredictionApp {
    public static void main(String[] args) {
        HistoricalDataAnalyzer analyzer = new HistoricalDataAnalyzer();

        analyzer.addEvent(new DisasterEvent("Hurricane", LocalDate.of(2022, 6, 10), 7.5));
        analyzer.addEvent(new DisasterEvent("Hurricane", LocalDate.of(2023, 8, 22), 8.0));
        analyzer.addEvent(new DisasterEvent("Earthquake", LocalDate.of(2023, 3, 5), 5.2));
        analyzer.addEvent(new DisasterEvent("Earthquake", LocalDate.of(2024, 1, 15), 6.3));
        analyzer.addEvent(new DisasterEvent("Flood", LocalDate.of(2021, 9, 9), 3.7));

        DisasterPredictor predictor = new DisasterPredictor(analyzer);

        System.out.println("Prediction for Hurricanes: " + predictor.predict("Hurricane"));
        System.out.println("Prediction for Earthquakes: " + predictor.predict("Earthquake"));
        System.out.println("Prediction for Floods: " + predictor.predict("Flood"));
    }
}