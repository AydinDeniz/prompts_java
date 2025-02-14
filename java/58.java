// Qubit.java
import java.util.Random;

public class Qubit {
    private boolean isOne; // Represents |1>

    public Qubit(boolean isOne) {
        this.isOne = isOne;
    }

    public Qubit() {
        this.isOne = new Random().nextBoolean(); // Random initialization
    }

    public boolean isOne() {
        return isOne;
    }

    @Override
    public String toString() {
        return isOne ? "|1>" : "|0>";
    }
}

// QuantumState.java
public class QuantumState {
    private Qubit[] qubits;

    public QuantumState(int size) {
        qubits = new Qubit[size];
        for (int i = 0; i < size; i++) {
            qubits[i] = new Qubit(); // Initialize each qubit randomly
        }
    }

    public Qubit[] getQubits() {
        return qubits;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Qubit qubit : qubits) {
            sb.append(qubit.toString());
        }
        return sb.toString();
    }
}

// GroverAlgorithm.java
import java.util.Random;

public class GroverAlgorithm {
    private QuantumState state;
    private int iterations;

    public GroverAlgorithm(QuantumState initialState, int iterations) {
        this.state = initialState;
        this.iterations = iterations;
    }

    public QuantumState execute() {
        System.out.println("Executing Grover's Algorithm for " + iterations + " iterations...");
        Random rand = new Random();
        for (int i = 0; i < iterations; i++) {
            int indexToFlip = rand.nextInt(state.getQubits().length);
            Qubit qubit = state.getQubits()[indexToFlip];
            state.getQubits()[indexToFlip] = new Qubit(!qubit.isOne()); // Invert randomly chosen qubit
            System.out.println("Iteration " + (i + 1) + ": State = " + state);
        }
        return state;
    }
}

// ShorAlgorithm.java
public class ShorAlgorithm {
    public long execute(long n) {
        System.out.println("Executing Shor's Algorithm on n = " + n);
        long factor = findFactor(n);
        System.out.println("Factor found: " + factor);
        return factor;
    }

    private long findFactor(long n) {
        for (long i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return i; // Return first non-trivial factor
            }
        }
        return n; // n is prime
    }
}

// QuantumAlgorithmVisualizer.java
public class QuantumAlgorithmVisualizer {
    public void visualizeState(QuantumState state) {
        System.out.println("Visualizing Quantum State: " + state);
        // Placeholder for visualization logic
    }

    public void visualizeAlgorithm(String algorithmName, QuantumState finalState) {
        System.out.println("Visualizing " + algorithmName + " Algorithm Result: " + finalState);
        // Placeholder for visualization logic
    }
}

// QuantumSimulationApp.java
public class QuantumSimulationApp {
    public static void main(String[] args) {
        QuantumAlgorithmVisualizer visualizer = new QuantumAlgorithmVisualizer();

        // Simulate Grover's Algorithm
        QuantumState groverInitialState = new QuantumState(5);
        GroverAlgorithm groverAlgorithm = new GroverAlgorithm(groverInitialState, 3);
        QuantumState groverFinalState = groverAlgorithm.execute();
        visualizer.visualizeAlgorithm("Grover's", groverFinalState);

        // Simulate Shor's Algorithm
        ShorAlgorithm shorAlgorithm = new ShorAlgorithm();
        long numberToFactor = 15; // Example number
        long factor = shorAlgorithm.execute(numberToFactor);
        System.out.println("Non-trivial factor of " + numberToFactor + " is " + factor);
    }
}