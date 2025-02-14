// Note.java
public class Note {
    private String pitch;
    private int duration; // in milliseconds

    public Note(String pitch, int duration) {
        this.pitch = pitch;
        this.duration = duration;
    }

    public String getPitch() {
        return pitch;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Note[Pitch: " + pitch + ", Duration: " + duration + "ms]";
    }
}

// Composition.java
import java.util.ArrayList;
import java.util.List;

public class Composition {
    private String title;
    private List<Note> notes;

    public Composition(String title) {
        this.title = title;
        this.notes = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public List<Note> getNotes() {
        return notes;
    }

    @Override
    public String toString() {
        return "Composition[Title: " + title + ", Notes: " + notes + "]";
    }
}

// AIComposer.java
import java.util.Random;

public class AIComposer {
    private static final String[] PITCHES = { "C", "D", "E", "F", "G", "A", "B" };
    private Random random;

    public AIComposer() {
        this.random = new Random();
    }

    public Note generateNote() {
        String pitch = PITCHES[random.nextInt(PITCHES.length)];
        int duration = (random.nextInt(4) + 1) * 500; // Random duration between 500 and 2000 ms
        return new Note(pitch, duration);
    }

    public void enhanceComposition(Composition composition, int numberOfNotes) {
        for (int i = 0; i < numberOfNotes; i++) {
            Note newNote = generateNote();
            composition.addNote(newNote);
            System.out.println("AI added: " + newNote);
        }
    }
}

// MusicAnalyzer.java
public class MusicAnalyzer {

    public void analyzeComposition(Composition composition) {
        int totalDuration = composition.getNotes().stream().mapToInt(Note::getDuration).sum();
        System.out.println("Analyzing composition: " + composition.getTitle());
        System.out.println("Total Duration: " + totalDuration + " ms");
        System.out.println("Number of Notes: " + composition.getNotes().size());
        // Further analysis can be implemented here
    }
}

// MusicCompositionApp.java
public class MusicCompositionApp {
    public static void main(String[] args) {
        Composition myComposition = new Composition("My First Composition");
        myComposition.addNote(new Note("C", 500));
        myComposition.addNote(new Note("E", 1000));
        myComposition.addNote(new Note("G", 1500));

        AIComposer aiComposer = new AIComposer();
        aiComposer.enhanceComposition(myComposition, 3);

        MusicAnalyzer analyzer = new MusicAnalyzer();
        analyzer.analyzeComposition(myComposition);

        System.out.println("\nFinal Composition: " + myComposition);
    }
}