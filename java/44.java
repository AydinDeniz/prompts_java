// ARObject.java
public class ARObject {
    private String name;
    private String description;
    private String modelFile;

    public ARObject(String name, String description, String modelFile) {
        this.name = name;
        this.description = description;
        this.modelFile = modelFile;
    }

    public void display() {
        System.out.println("Displaying AR Object: " + name);
        System.out.println("Description: " + description);
        System.out.println("3D Model File: " + modelFile);
    }
}

// Lesson.java
import java.util.ArrayList;
import java.util.List;

public class Lesson {
    private String title;
    private List<ARObject> arObjects;

    public Lesson(String title) {
        this.title = title;
        this.arObjects = new ArrayList<>();
    }

    public void addARObject(ARObject arObject) {
        arObjects.add(arObject);
    }

    public void start() {
        System.out.println("Starting lesson: " + title);
        for (ARObject arObject : arObjects) {
            arObject.display();
        }
    }
}

// ARLearningSystem.java
import java.util.ArrayList;
import java.util.List;

public class ARLearningSystem {
    private List<Lesson> lessons;

    public ARLearningSystem() {
        this.lessons = new ArrayList<>();
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public void startLesson(String title) {
        for (Lesson lesson : lessons) {
            if (lesson.title.equals(title)) {
                lesson.start();
                return;
            }
        }
        System.out.println("Lesson not found: " + title);
    }

    public void listLessons() {
        System.out.println("Available Lessons:");
        for (Lesson lesson : lessons) {
            System.out.println("- " + lesson.title);
        }
    }
}

// ARLearningApp.java
public class ARLearningApp {
    public static void main(String[] args) {
        ARLearningSystem learningSystem = new ARLearningSystem();

        ARObject solarSystem = new ARObject("Solar System", "A model of the solar system showing orbits.", "solar_system.obj");
        ARObject humanHeart = new ARObject("Human Heart", "An anatomical representation of the human heart.", "human_heart.obj");

        Lesson astronomyLesson = new Lesson("Astronomy Basics");
        astronomyLesson.addARObject(solarSystem);

        Lesson biologyLesson = new Lesson("Human Anatomy");
        biologyLesson.addARObject(humanHeart);

        learningSystem.addLesson(astronomyLesson);
        learningSystem.addLesson(biologyLesson);

        learningSystem.listLessons();
        
        System.out.println("\nStarting an AR lesson...");
        learningSystem.startLesson("Astronomy Basics");
    }
}