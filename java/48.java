// Product.java
public class Product {
    private String id;
    private String name;
    private String origin;
    
    public Product(String id, String name, String origin) {
        this.id = id;
        this.name = name;
        this.origin = origin;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOrigin() {
        return origin;
    }
    
    @Override
    public String toString() {
        return "Product[ID: " + id + ", Name: " + name + ", Origin: " + origin + "]";
    }
}

// Block.java
import java.util.Date;

public class Block {
    private int index;
    private long timestamp;
    private String data;
    private String previousHash;
    private String hash;
    
    public Block(int index, String data, String previousHash) {
        this.index = index;
        this.data = data;
        this.previousHash = previousHash;
        this.timestamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String dataToHash = index + Long.toString(timestamp) + data + previousHash;
        int hash = dataToHash.hashCode();
        return Integer.toString(hash);
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    @Override
    public String toString() {
        return "Block[Idx: " + index + ", Data: " + data + ", PrevHash: " + previousHash + ", Hash: " + hash + "]";
    }
}

// Blockchain.java
import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private List<Block> chain;

    public Blockchain() {
        chain = new ArrayList<>();
        chain.add(createGenesisBlock());
    }

    private Block createGenesisBlock() {
        return new Block(0, "Genesis Block", "0");
    }

    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    public void addBlock(String data) {
        Block newBlock = new Block(chain.size(), data, getLatestBlock().getHash());
        chain.add(newBlock);
    }

    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }

            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Block block : chain) {
            sb.append(block.toString()).append("\n");
        }
        return sb.toString();
    }
}

// SupplyChain.java
public class SupplyChain {
    private Blockchain blockchain;

    public SupplyChain() {
        blockchain = new Blockchain();
    }

    public void trackProduct(Product product) {
        String productData = product.toString();
        blockchain.addBlock(productData);
    }

    public boolean verifySupplyChain() {
        return blockchain.isChainValid();
    }

    public void displaySupplyChain() {
        System.out.println(blockchain);
    }
}

// SupplyChainApp.java
public class SupplyChainApp {
    public static void main(String[] args) {
        SupplyChain supplyChain = new SupplyChain();

        Product product1 = new Product("P001", "Widget", "Factory A");
        Product product2 = new Product("P002", "Gadget", "Factory B");

        supplyChain.trackProduct(product1);
        supplyChain.trackProduct(product2);

        System.out.println("Displaying Supply Chain:");
        supplyChain.displaySupplyChain();

        System.out.println("Is supply chain valid? " + supplyChain.verifySupplyChain());
    }
}