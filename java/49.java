// HealthMetric.java
public class HealthMetric {
    private String type;
    private double value;
    private String unit;
    
    public HealthMetric(String type, double value, String unit) {
        this.type = type;
        this.value = value;
        this.unit = unit;
    }

    public String getType() {
        return type;
    }

    public double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return "HealthMetric[type=" + type + ", value=" + value + " " + unit + "]";
    }
}

// HealthMonitor.java
import java.util.ArrayList;
import java.util.List;

public class HealthMonitor {
    private List<HealthMetric> metrics;
    
    public HealthMonitor() {
        this.metrics = new ArrayList<>();
    }
    
    public void addMetric(HealthMetric metric) {
        metrics.add(metric);
    }

    public List<HealthMetric> getMetrics() {
        return metrics;
    }

    public void displayMetrics() {
        for (HealthMetric metric : metrics) {
            System.out.println(metric);
        }
    }
}

// HealthAdvisor.java
public class HealthAdvisor {
    public String recommend(HealthMonitor monitor) {
        StringBuilder advice = new StringBuilder();
        
        for (HealthMetric metric : monitor.getMetrics()) {
            switch (metric.getType()) {
                case "Heart Rate":
                    advice.append(recommendForHeartRate(metric.getValue())).append("\n");
                    break;
                case "Blood Pressure":
                    advice.append(recommendForBloodPressure(metric.getValue())).append("\n");
                    break;
                case "Steps":
                    advice.append(recommendForSteps(metric.getValue())).append("\n");
                    break;
                default:
                    advice.append("No advice available for ").append(metric.getType()).append("\n");
            }
        }
        
        return advice.toString();
    }

    private String recommendForHeartRate(double value) {
        if (value < 60) {
            return "Heart Rate is low: Consider consulting a doctor.";
        } else if (value > 100) {
            return "Heart Rate is high: Take a rest and stay hydrated.";
        }
        return "Heart Rate is normal.";
    }

    private String recommendForBloodPressure(double value) {
        if (value < 80) {
            return "Blood Pressure is low: Increase salt intake moderately.";
        } else if (value > 140) {
            return "Blood Pressure is high: Reduce salt intake and exercise regularly.";
        }
        return "Blood Pressure is normal.";
    }

    private String recommendForSteps(double value) {
        if (value < 5000) {
            return "Steps are low: Try to walk more to reach at least 10,000 steps a day.";
        }
        return "Great! You're active.";
    }
}

// HealthMonitoringApp.java
public class HealthMonitoringApp {
    public static void main(String[] args) {
        HealthMonitor monitor = new HealthMonitor();
        
        monitor.addMetric(new HealthMetric("Heart Rate", 72, "bpm"));
        monitor.addMetric(new HealthMetric("Blood Pressure", 135, "mmHg"));
        monitor.addMetric(new HealthMetric("Steps", 9000, "steps"));
        
        System.out.println("Current Health Metrics:");
        monitor.displayMetrics();
        
        HealthAdvisor advisor = new HealthAdvisor();
        String recommendations = advisor.recommend(monitor);
        
        System.out.println("\nHealth Advice:");
        System.out.println(recommendations);
    }
}