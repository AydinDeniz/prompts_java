import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class File {
    private String name;
    private String content;

    public File(String name) {
        this.name = name;
        this.content = "";
    }

    public String getName() {
        return name;
    }

    public void writeContent(String newContent) {
        this.content = newContent;
    }

    public String readContent() {
        return content;
    }
}

class Directory {
    private String name;
    private Map<String, File> files;
    private Map<String, Directory> subdirectories;

    public Directory(String name) {
        this.name = name;
        this.files = new HashMap<>();
        this.subdirectories = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public File createFile(String name) {
        File newFile = new File(name);
        files.put(name, newFile);
        return newFile;
    }

    public Directory createSubdirectory(String name) {
        Directory newDir = new Directory(name);
        subdirectories.put(name, newDir);
        return newDir;
    }

    public File getFile(String name) {
        return files.get(name);
    }

    public Directory getSubdirectory(String name) {
        return subdirectories.get(name);
    }

    public void listContents() {
        System.out.println("Directory: " + name);
        System.out.println("Files:");
        for (String fileName : files.keySet()) {
            System.out.println(" - " + fileName);
        }
        System.out.println("Subdirectories:");
        for (String dirName : subdirectories.keySet()) {
            System.out.println(" - " + dirName);
        }
    }
}

public class VirtualFileSystem {
    private Directory root;

    public VirtualFileSystem() {
        root = new Directory("root");
    }

    public Directory getRoot() {
        return root;
    }

    public static void main(String[] args) {
        VirtualFileSystem vfs = new VirtualFileSystem();
        Directory root = vfs.getRoot();

        File file1 = root.createFile("file1.txt");
        file1.writeContent("Hello, this is file1.");

        Directory subDir = root.createSubdirectory("subDir");
        File file2 = subDir.createFile("file2.txt");
        file2.writeContent("This is file2 inside subDir.");

        root.listContents();
        subDir.listContents();

        System.out.println("Reading file1.txt: " + file1.readContent());
        System.out.println("Reading file2.txt: " + file2.readContent());
    }
}