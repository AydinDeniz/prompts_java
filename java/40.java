// ComplexNumber.java
public class ComplexNumber {
    private double real;
    private double imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }

    public ComplexNumber add(ComplexNumber other) {
        return new ComplexNumber(this.real + other.real, this.imaginary + other.imaginary);
    }

    public ComplexNumber multiply(ComplexNumber other) {
        double realPart = this.real * other.real - this.imaginary * other.imaginary;
        double imagPart = this.real * other.imaginary + this.imaginary * other.real;
        return new ComplexNumber(realPart, imagPart);
    }

    @Override
    public String toString() {
        return String.format("%.2f + %.2fi", real, imaginary);
    }
}

// Qubit.java
public class Qubit {
    private ComplexNumber state0;
    private ComplexNumber state1;

    public Qubit(ComplexNumber state0, ComplexNumber state1) {
        this.state0 = state0;
        this.state1 = state1;
    }

    public ComplexNumber getState0() {
        return state0;
    }

    public ComplexNumber getState1() {
        return state1;
    }

    @Override
    public String toString() {
        return "[" + state0 + " |0> + " + state1 + " |1>]";
    }
}

// QuantumGate.java
public abstract class QuantumGate {
    public abstract Qubit apply(Qubit qubit);
}

// HadamardGate.java
public class HadamardGate extends QuantumGate {
    private ComplexNumber hCoefficient = new ComplexNumber(1 / Math.sqrt(2), 0);

    @Override
    public Qubit apply(Qubit qubit) {
        ComplexNumber newState0 = hCoefficient.multiply(qubit.getState0().add(qubit.getState1()));
        ComplexNumber newState1 = hCoefficient.multiply(qubit.getState0().add(qubit.getState1().multiply(new ComplexNumber(-1, 0))));
        return new Qubit(newState0, newState1);
    }
}

// QuantumCircuit.java
import java.util.ArrayList;
import java.util.List;

public class QuantumCircuit {
    private List<Qubit> qubits;

    public QuantumCircuit() {
        this.qubits = new ArrayList<>();
    }

    public void addQubit(Qubit qubit) {
        qubits.add(qubit);
    }

    public void applyGate(QuantumGate gate) {
        for (int i = 0; i < qubits.size(); i++) {
            qubits.set(i, gate.apply(qubits.get(i)));
        }
    }

    public void displayCircuitState() {
        for (Qubit qubit : qubits) {
            System.out.println(qubit);
        }
    }
}

// QuantumSimulatorApp.java
public class QuantumSimulatorApp {
    public static void main(String[] args) {
        Qubit qubit1 = new Qubit(new ComplexNumber(1, 0), new ComplexNumber(0, 0)); // |0>
        Qubit qubit2 = new Qubit(new ComplexNumber(0, 0), new ComplexNumber(1, 0)); // |1>

        QuantumCircuit circuit = new QuantumCircuit();
        circuit.addQubit(qubit1);
        circuit.addQubit(qubit2);

        System.out.println("Initial Circuit State:");
        circuit.displayCircuitState();

        HadamardGate hGate = new HadamardGate();
        circuit.applyGate(hGate);

        System.out.println("Circuit State after Hadamard Gate:");
        circuit.displayCircuitState();
    }
}