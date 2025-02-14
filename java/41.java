// Face.java
public class Face {
    private String id;
    private String encodedFaceData; // Simulated encoded face data

    public Face(String id, String encodedFaceData) {
        this.id = id;
        this.encodedFaceData = encodedFaceData;
    }

    public String getId() {
        return id;
    }

    public String getEncodedFaceData() {
        return encodedFaceData;
    }
}

// FaceDatabase.java
import java.util.HashMap;
import java.util.Map;

public class FaceDatabase {
    private Map<String, Face> faceMap;

    public FaceDatabase() {
        this.faceMap = new HashMap<>();
    }

    public void registerFace(Face face) {
        faceMap.put(face.getId(), face);
    }

    public Face getFace(String faceId) {
        return faceMap.get(faceId);
    }
}

// FacialRecognitionSystem.java
import java.util.Random;

public class FacialRecognitionSystem {
    private FaceDatabase faceDatabase;

    public FacialRecognitionSystem(FaceDatabase faceDatabase) {
        this.faceDatabase = faceDatabase;
    }

    public boolean authenticate(String faceData) {
        for (Face face : faceDatabase.faceMap.values()) {
            // Simulated face recognition logic
            if (face.getEncodedFaceData().equals(faceData)) {
                System.out.println("Authentication successful for Face ID: " + face.getId());
                return true;
            }
        }
        System.out.println("Authentication failed.");
        return false;
    }

    public String captureFaceData() {
        // Simulated capturing of face data
        Random random = new Random();
        return "face_data_" + random.nextInt(100); // Random face data representation
    }
}

// AccessControlSystem.java
public class AccessControlSystem {
    private FacialRecognitionSystem recognitionSystem;

    public AccessControlSystem(FacialRecognitionSystem recognitionSystem) {
        this.recognitionSystem = recognitionSystem;
    }

    public void grantAccess(String faceData) {
        if (recognitionSystem.authenticate(faceData)) {
            System.out.println("Access granted.");
        } else {
            System.out.println("Access denied.");
        }
    }
}

// FacialRecognitionApp.java
public class FacialRecognitionApp {
    public static void main(String[] args) {
        // Initialize face database and register known faces
        FaceDatabase database = new FaceDatabase();
        database.registerFace(new Face("F001", "face_data_42"));
        database.registerFace(new Face("F002", "face_data_85"));

        FacialRecognitionSystem recognitionSystem = new FacialRecognitionSystem(database);
        AccessControlSystem accessControl = new AccessControlSystem(recognitionSystem);

        // Simulate face data capture
        String capturedFaceData = recognitionSystem.captureFaceData();
        System.out.println("Captured Face Data: " + capturedFaceData);

        // Attempt to access
        accessControl.grantAccess(capturedFaceData);
    }
}