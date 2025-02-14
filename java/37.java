// VRHeadset.java
public class VRHeadset {
    private boolean isConnected;

    public VRHeadset() {
        this.isConnected = false;
    }

    public void connect() {
        isConnected = true;
        System.out.println("VR Headset connected.");
    }

    public void disconnect() {
        isConnected = false;
        System.out.println("VR Headset disconnected.");
    }

    public boolean isConnected() {
        return isConnected;
    }
}

// HapticFeedback.java
public class HapticFeedback {
    public void activate() {
        System.out.println("Haptic feedback activated.");
    }

    public void deactivate() {
        System.out.println("Haptic feedback deactivated.");
    }
}

// MedicalProcedure.java
import java.util.*;

public class MedicalProcedure {
    private String name;
    private List<String> steps;
    private int currentStep;

    public MedicalProcedure(String name, List<String> steps) {
        this.name = name;
        this.steps = steps;
        this.currentStep = 0;
    }

    public void performStep() {
        if (currentStep < steps.size()) {
            System.out.println("Performing step: " + steps.get(currentStep));
            currentStep++;
        } else {
            System.out.println("Procedure completed.");
        }
    }

    public boolean isCompleted() {
        return currentStep >= steps.size();
    }

    public void resetProcedure() {
        currentStep = 0;
    }
}

// VirtualEnvironment.java
import java.util.*;

public class VirtualEnvironment {
    private List<MedicalProcedure> procedures;

    public VirtualEnvironment() {
        this.procedures = new ArrayList<>();
    }

    public void addProcedure(MedicalProcedure procedure) {
        procedures.add(procedure);
    }

    public void startProcedure(String procedureName) {
        for (MedicalProcedure procedure : procedures) {
            if (procedureName.equals(procedure.name)) {
                System.out.println("Starting procedure: " + procedureName);
                while (!procedure.isCompleted()) {
                    procedure.performStep();
                    simulateHapticFeedback();
                }
                System.out.println("Procedure " + procedureName + " completed in virtual environment.");
                procedure.resetProcedure();
                return;
            }
        }
        System.out.println("Procedure " + procedureName + " not found.");
    }

    private void simulateHapticFeedback() {
        HapticFeedback haptic = new HapticFeedback();
        haptic.activate();
        haptic.deactivate();
    }
}

// VRMedicalTrainingSimulator.java
public class VRMedicalTrainingSimulator {
    public static void main(String[] args) {
        VRHeadset headset = new VRHeadset();
        headset.connect();

        VirtualEnvironment env = new VirtualEnvironment();
        List<String> surgerySteps = Arrays.asList(
                "Incision made",
                "Organ exposure",
                "Surgical repair",
                "Closing incision"
        );
        MedicalProcedure surgery = new MedicalProcedure("Appendectomy", surgerySteps);
        env.addProcedure(surgery);

        if (headset.isConnected()) {
            env.startProcedure("Appendectomy");
        }

        headset.disconnect();
    }
}