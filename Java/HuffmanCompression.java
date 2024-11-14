
import java.io.*;
import java.util.*;

// Node class for Huffman Tree
class HuffmanNode implements Comparable<HuffmanNode> {
    char character;
    int frequency;
    HuffmanNode left, right;

    public HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(HuffmanNode node) {
        return this.frequency - node.frequency;
    }
}

public class HuffmanCompression {
    private Map<Character, String> huffmanCode = new HashMap<>();
    private HuffmanNode root;

    // Build the Huffman tree
    public void buildTree(Map<Character, Integer> frequencyMap) {
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();

        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            priorityQueue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();
            int sum = left.frequency + right.frequency;
            priorityQueue.add(new HuffmanNode(sum, left, right));
        }

        root = priorityQueue.poll();
        generateCodes(root, "");
    }

    // Generate Huffman codes from the tree
    private void generateCodes(HuffmanNode node, String code) {
        if (node == null) return;
        if (node.left == null && node.right == null) {
            huffmanCode.put(node.character, code);
        }
        generateCodes(node.left, code + "0");
        generateCodes(node.right, code + "1");
    }

    // Compress the input file
    public void compress(String inputFilePath, String outputFilePath) throws IOException {
        Map<Character, Integer> frequencyMap = buildFrequencyMap(inputFilePath);
        buildTree(frequencyMap);

        StringBuilder encodedData = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            int ch;
            while ((ch = reader.read()) != -1) {
                encodedData.append(huffmanCode.get((char) ch));
            }
        }

        try (BitOutputStream bitOutputStream = new BitOutputStream(new FileOutputStream(outputFilePath))) {
            for (char bit : encodedData.toString().toCharArray()) {
                bitOutputStream.writeBit(bit == '1');
            }
        }
    }

    // Decompress the file
    public void decompress(String compressedFilePath, String outputFilePath) throws IOException {
        StringBuilder decodedData = new StringBuilder();
        try (BitInputStream bitInputStream = new BitInputStream(new FileInputStream(compressedFilePath))) {
            HuffmanNode node = root;
            Boolean bit;
            while ((bit = bitInputStream.readBit()) != null) {
                node = bit ? node.right : node.left;
                if (node.left == null && node.right == null) {
                    decodedData.append(node.character);
                    node = root;
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write(decodedData.toString());
        }
    }

    // Build a frequency map from the input file
    private Map<Character, Integer> buildFrequencyMap(String filePath) throws IOException {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int ch;
            while ((ch = reader.read()) != -1) {
                frequencyMap.put((char) ch, frequencyMap.getOrDefault((char) ch, 0) + 1);
            }
        }
        return frequencyMap;
    }

    public static void main(String[] args) throws IOException {
        HuffmanCompression huffman = new HuffmanCompression();
        huffman.compress("input.txt", "compressed.bin");
        huffman.decompress("compressed.bin", "output.txt");
        System.out.println("Compression and Decompression completed.");
    }
}

// Custom BitOutputStream for writing bits to a file
class BitOutputStream implements Closeable {
    private final OutputStream output;
    private int currentByte;
    private int numBitsFilled;

    public BitOutputStream(OutputStream output) {
        this.output = output;
        this.currentByte = 0;
        this.numBitsFilled = 0;
    }

    public void writeBit(boolean bit) throws IOException {
        if (bit) currentByte |= (1 << (7 - numBitsFilled));
        numBitsFilled++;
        if (numBitsFilled == 8) {
            output.write(currentByte);
            currentByte = 0;
            numBitsFilled = 0;
        }
    }

    @Override
    public void close() throws IOException {
        if (numBitsFilled > 0) {
            output.write(currentByte);
        }
        output.close();
    }
}

// Custom BitInputStream for reading bits from a file
class BitInputStream implements Closeable {
    private final InputStream input;
    private int currentByte;
    private int numBitsRemaining;

    public BitInputStream(InputStream input) {
        this.input = input;
        this.currentByte = 0;
        this.numBitsRemaining = 0;
    }

    public Boolean readBit() throws IOException {
        if (numBitsRemaining == 0) {
            currentByte = input.read();
            if (currentByte == -1) return null;
            numBitsRemaining = 8;
        }
        numBitsRemaining--;
        return ((currentByte >> numBitsRemaining) & 1) == 1;
    }

    @Override
    public void close() throws IOException {
        input.close();
    }
}
