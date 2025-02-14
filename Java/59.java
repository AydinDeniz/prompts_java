// Task.java
public class Task {
    private String name;
    private String description;
    private boolean isCompleted;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.isCompleted = false;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void completeTask() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        return "Task[Name: " + name + ", Description: " + description + ", Completed: " + isCompleted + "]";
    }
}

// Robot.java
import java.util.ArrayList;
import java.util.List;

public class Robot {
    private String id;
    private List<Task> taskQueue;

    public Robot(String id) {
        this.id = id;
        this.taskQueue = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void addTask(Task task) {
        taskQueue.add(task);
        System.out.println("Task added to Robot " + id + ": " + task.getDescription());
    }

    public void performTasks() {
        System.out.println("Robot " + id + " starting task execution...");
        for (Task task : taskQueue) {
            System.out.println("Executing: " + task.getDescription());
            task.completeTask();
        }
    }

    public List<Task> getTaskQueue() {
        return taskQueue;
    }
}

// HumanWorker.java
import java.util.ArrayList;
import java.util.List;

public class HumanWorker {
    private String name;
    private List<Task> taskList;

    public HumanWorker(String name) {
        this.name = name;
        this.taskList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void assignTask(Task task) {
        taskList.add(task);
        System.out.println("Task assigned to Human Worker " + name + ": " + task.getDescription());
    }

    public void performTasks() {
        System.out.println("Human Worker " + name + " starting task execution...");
        for (Task task : taskList) {
            System.out.println("Executing: " + task.getDescription());
            task.completeTask();
        }
    }

    public List<Task> getTaskList() {
        return taskList;
    }
}

// CoordinationPlatform.java
public class CoordinationPlatform {
    private Robot robot;
    private HumanWorker humanWorker;

    public CoordinationPlatform(Robot robot, HumanWorker humanWorker) {
        this.robot = robot;
        this.humanWorker = humanWorker;
    }

    public void assignTaskToRobot(Task task) {
        robot.addTask(task);
    }

    public void assignTaskToHuman(Task task) {
        humanWorker.assignTask(task);
    }

    public void startTaskExecution() {
        robot.performTasks();
        humanWorker.performTasks();
    }

    public void displayTaskStatus() {
        System.out.println("\nTask Status:");
        for (Task task : robot.getTaskQueue()) {
            System.out.println(task);
        }
        for (Task task : humanWorker.getTaskList()) {
            System.out.println(task);
        }
    }
}

// HumanRobotInteractionApp.java
public class HumanRobotInteractionApp {
    public static void main(String[] args) {
        Robot robot = new Robot("R1");
        HumanWorker humanWorker = new HumanWorker("Alice");
        CoordinationPlatform platform = new CoordinationPlatform(robot, humanWorker);

        Task task1 = new Task("Assemble Parts", "Assemble mechanical parts in sequence.");
        Task task2 = new Task("Inspect Quality", "Inspect the quality of assembled products.");
        Task task3 = new Task("Transport Goods", "Transport finished goods to storage.");
        Task task4 = new Task("Maintenance Check", "Perform maintenance check on equipment.");

        platform.assignTaskToRobot(task1);
        platform.assignTaskToRobot(task3);
        platform.assignTaskToHuman(task2);
        platform.assignTaskToHuman(task4);

        platform.startTaskExecution();
        platform.displayTaskStatus();
    }
}