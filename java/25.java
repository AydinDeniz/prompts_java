import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class File {
    private String name;
    private String content;

    public File(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }
}

class Node {
    private String nodeName;
    private Map<String, File> files;
    private List<Node> networkNodes;

    public Node(String nodeName) {
        this.nodeName = nodeName;
        this.files = new HashMap<>();
        this.networkNodes = new ArrayList<>();
    }

    public void addFile(File file) {
        files.put(file.getName(), file);
    }

    public void connectToNode(Node node) {
        networkNodes.add(node);
    }

    public File searchFile(String fileName) {
        if (files.containsKey(fileName)) {
            System.out.println(nodeName + " has the file: " + fileName);
            return files.get(fileName);
        } else {
            for (Node node : networkNodes) {
                File file = node.searchFile(fileName);
                if (file != null) {
                    return file;
                }
            }
        }
        return null;
    }
}

public class P2PFileSharing {
    public static void main(String[] args) {
        Node nodeA = new Node("NodeA");
        Node nodeB = new Node("NodeB");
        Node nodeC = new Node("NodeC");

        nodeA.addFile(new File("file1.txt", "Content of file1"));
        nodeB.addFile(new File("file2.txt", "Content of file2"));
        nodeC.addFile(new File("file3.txt", "Content of file3"));

        nodeA.connectToNode(nodeB);
        nodeA.connectToNode(nodeC);
        nodeB.connectToNode(nodeC);

        File file = nodeA.searchFile("file2.txt");
        if (file != null) {
            System.out.println("Found: " + file.getName() + " with content: " + file.getContent());
        } else {
            System.out.println("File not found.");
        }
    }
}