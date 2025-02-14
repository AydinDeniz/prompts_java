import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.util.*;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

class User {
    private String username;
    private String passwordHash;

    public User(String username, String password) {
        this.username = username;
        this.passwordHash = hashPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String password) {
        return hashPassword(password).equals(passwordHash);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

class CloudStorage {
    private Map<String, User> users;
    private Path storageDir;
    private static final String ENCRYPTION_KEY = "0123456789abcdef";
    
    public CloudStorage(String dir) throws IOException {
        users = new HashMap<>();
        storageDir = Paths.get(dir);
        if (!Files.exists(storageDir)) {
            Files.createDirectories(storageDir);
        }
    }

    public void registerUser(String username, String password) {
        users.put(username, new User(username, password));
    }

    public User login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.authenticate(password)) {
            System.out.println("Welcome back, " + username + "!");
            return user;
        }
        System.out.println("Authentication failed!");
        return null;
    }

    public void uploadFile(User user, String filename, byte[] content) throws Exception {
        byte[] encryptedContent = encrypt(content);
        Files.write(storageDir.resolve(user.getUsername() + "_" + filename), encryptedContent);
        System.out.println("File " + filename + " uploaded successfully.");
    }

    public byte[] downloadFile(User user, String filename) throws Exception {
        Path file = storageDir.resolve(user.getUsername() + "_" + filename);
        if (Files.exists(file)) {
            byte[] encryptedContent = Files.readAllBytes(file);
            return decrypt(encryptedContent);
        }
        System.out.println("File not found.");
        return null;
    }

    public void deleteFile(User user, String filename) throws IOException {
        Path file = storageDir.resolve(user.getUsername() + "_" + filename);
        if (Files.exists(file)) {
            Files.delete(file);
            System.out.println("File " + filename + " deleted successfully.");
        } else {
            System.out.println("File not found.");
        }
    }

    private byte[] encrypt(byte[] data) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    private byte[] decrypt(byte[] data) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }
}

public class CloudStorageApp {
    public static void main(String[] args) throws Exception {
        CloudStorage storage = new CloudStorage("storage");

        storage.registerUser("alice", "password123");
        storage.registerUser("bob", "secure!");

        User alice = storage.login("alice", "password123");
        if (alice != null) {
            storage.uploadFile(alice, "hello.txt", "Hello, World!".getBytes());
            byte[] fileContent = storage.downloadFile(alice, "hello.txt");
            System.out.println("Downloaded content: " + new String(fileContent));

            storage.deleteFile(alice, "hello.txt");
        }

        User bob = storage.login("bob", "secure!");
        if (bob != null) {
            storage.uploadFile(bob, "secureInfo.txt", "Top Secret Info".getBytes());
            byte[] fileContent = storage.downloadFile(bob, "secureInfo.txt");
            System.out.println("Downloaded content: " + new String(fileContent));

            storage.deleteFile(bob, "secureInfo.txt");
        }
    }
}