// Subject.java
import java.util.ArrayList;
import java.util.List;

public class Subject {
    private String name;
    private List<String> topics;

    public Subject(String name) {
        this.name = name;
        this.topics = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void addTopic(String topic) {
        topics.add(topic);
    }

    @Override
    public String toString() {
        return "Subject[Name: " + name + ", Topics: " + topics + "]";
    }
}

// Student.java
public class Student {
    private String studentId;
    private String name;
    private double progress; // Represents percentage of course completed

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.progress = 0.0;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public double getProgress() {
        return progress;
    }

    public void updateProgress(double increment) {
        this.progress = Math.min(100.0, this.progress + increment);
    }

    @Override
    public String toString() {
        return "Student[ID: " + studentId + ", Name: " + name + ", Progress: " + progress + "%]";
    }
}

// LearningPath.java
import java.util.*;

public class LearningPath {
    private Map<Subject, List<String>> learningSequence;

    public LearningPath() {
        this.learningSequence = new HashMap<>();
    }

    public void addSubject(Subject subject) {
        if (!learningSequence.containsKey(subject)) {
            learningSequence.put(subject, new ArrayList<>(subject.getTopics()));
        }
    }

    public String getNextTopic(Subject subject) {
        List<String> topics = learningSequence.get(subject);
        return (topics != null && !topics.isEmpty()) ? topics.get(0) : null;
    }

    public void completeTopic(Subject subject, String topic) {
        List<String> topics = learningSequence.get(subject);
        if (topics != null && topics.contains(topic)) {
            topics.remove(topic);
        }
    }

    public boolean hasMoreTopics(Subject subject) {
        List<String> topics = learningSequence.get(subject);
        return topics != null && !topics.isEmpty();
    }
}

// Tutor.java
public class Tutor {
    private LearningPath learningPath;

    public Tutor(LearningPath learningPath) {
        this.learningPath = learningPath;
    }

    public void teachStudentSubject(Student student, Subject subject) {
        System.out.println("Teaching subject: " + subject.getName() + " to " + student.getName());
        while (learningPath.hasMoreTopics(subject)) {
            String nextTopic = learningPath.getNextTopic(subject);
            if (nextTopic != null) {
                System.out.println("Teaching topic: " + nextTopic);
                learningPath.completeTopic(subject, nextTopic);
                student.updateProgress(5); // Assume each topic is worth 5%
            }
        }
        System.out.println("Completed subject: " + subject.getName() + " for " + student.getName());
    }
}

// Assessment.java
import java.util.Random;

public class Assessment {
    private Random random;

    public Assessment() {
        this.random = new Random();
    }

    public double assessStudent(Student student, Subject subject) {
        double score = 50 + (random.nextDouble() * 50); // Random score between 50 and 100
        System.out.println("Assessment completed for " + student.getName() + " on " + subject.getName() + ": Score = " + score);
        return score;
    }

    public void provideFeedback(double score) {
        if (score > 85) {
            System.out.println("Excellent performance. Keep up the great work!");
        } else if (score > 70) {
            System.out.println("Good job. Consider reviewing some of the material for additional mastery.");
        } else {
            System.out.println("Consider revisiting some topics for a better understanding.");
        }
    }
}

// IntelligentTutoringSystem.java
public class IntelligentTutoringSystem {
    private Map<Student, LearningPath> studentPaths;
    
    public IntelligentTutoringSystem() {
        this.studentPaths = new HashMap<>();
    }

    public void enrollStudentInSubject(Student student, Subject subject) {
        LearningPath path = new LearningPath();
        path.addSubject(subject);
        studentPaths.put(student, path);
    }

    public LearningPath getLearningPathForStudent(Student student) {
        return studentPaths.get(student);
    }
}

// TutoringSystemApp.java
public class TutoringSystemApp {
    public static void main(String[] args) {
        Subject math = new Subject("Mathematics");
        math.addTopic("Algebra");
        math.addTopic("Calculus");
        math.addTopic("Geometry");

        Student alice = new Student("STU001", "Alice Johnson");

        IntelligentTutoringSystem its = new IntelligentTutoringSystem();
        its.enrollStudentInSubject(alice, math);
        
        LearningPath alicePath = its.getLearningPathForStudent(alice);
        
        Tutor tutor = new Tutor(alicePath);
        tutor.teachStudentSubject(alice, math);

        Assessment assessment = new Assessment();
        double score = assessment.assessStudent(alice, math);
        assessment.provideFeedback(score);

        System.out.println("\nFinal Student Progress: " + alice);
    }
}