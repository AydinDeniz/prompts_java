// GameROM.java
public class GameROM {
    private String title;
    private byte[] data;

    public GameROM(String title, byte[] data) {
        this.title = title;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "GameROM[Title: " + title + "]";
    }
}

// CPU.java
public class CPU {
    private int programCounter;

    public CPU() {
        this.programCounter = 0;
    }

    public void executeInstruction(GameROM rom, Memory memory, Display display) {
        byte instruction = rom.getData()[programCounter];
        System.out.println("Executing instruction at PC=" + programCounter + ": " + instruction);
        programCounter = (programCounter + 1) % rom.getData().length;

        // Dummy operation for demonstration
        if (instruction % 2 == 0) {
            display.drawImage(instruction);
        } else {
            memory.storeData(instruction, instruction);
        }
    }
}

// Memory.java
import java.util.HashMap;
import java.util.Map;

public class Memory {
    private Map<Integer, Byte> storage;

    public Memory() {
        storage = new HashMap<>();
    }

    public void storeData(int address, byte data) {
        storage.put(address, data);
        System.out.println("Stored data at address " + address + ": " + data);
    }

    public byte retrieveData(int address) {
        return storage.getOrDefault(address, (byte) 0);
    }
}

// Display.java
public class Display {
    public void drawImage(byte data) {
        // Simulate drawing an image based on the data
        System.out.println("Drawing image with data: " + data);
    }
}

// Emulator.java
public class Emulator {
    private CPU cpu;
    private Memory memory;
    private Display display;

    public Emulator() {
        cpu = new CPU();
        memory = new Memory();
        display = new Display();
    }

    public void loadAndRun(GameROM rom) {
        System.out.println("Loading ROM: " + rom);
        int cycles = 5; // Simulating limited execution cycles for the demo
        for (int i = 0; i < cycles; i++) {
            cpu.executeInstruction(rom, memory, display);
        }
    }
}

// EmulatorApp.java
public class EmulatorApp {
    public static void main(String[] args) {
        byte[] dummyROMData = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
        GameROM rom = new GameROM("Dummy Game", dummyROMData);

        Emulator emulator = new Emulator();
        emulator.loadAndRun(rom);
    }
}