import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Block {
    private String hash;
    private String previousHash;
    private String data;
    private long timeStamp;
    private int nonce;

    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = System.currentTimeMillis();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String input = previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + data;
        return applySha256(input);
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0'); 
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!! : " + hash);
    }

    public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }
}

class Blockchain {
    private List<Block> chain;
    private int difficulty;

    public Blockchain(int difficulty) {
        this.chain = new ArrayList<>();
        this.difficulty = difficulty;
        chain.add(createGenesisBlock());
    }

    private Block createGenesisBlock() {
        return new Block("Genesis Block", "0");
    }

    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    public void addBlock(Block newBlock) {
        newBlock.mineBlock(difficulty);
        chain.add(newBlock);
    }

    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                System.out.println("Current hashes not equal");
                return false;
            }
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                System.out.println("Previous hashes not equal");
                return false;
            }
        }
        return true;
    }
}

class VotingSystem {
    private Blockchain blockchain;
    private Map<String, Integer> candidates;
    private List<String> voters;

    public VotingSystem(int difficulty) {
        this.blockchain = new Blockchain(difficulty);
        this.candidates = new HashMap<>();
        this.voters = new ArrayList<>();
        initializeCandidates();
    }

    private void initializeCandidates() {
        candidates.put("Alice", 0);
        candidates.put("Bob", 0);
    }

    public void castVote(String voterId, String candidate) {
        if (voters.contains(voterId)) {
            System.out.println("Voter has already voted.");
            return;
        }
        if (!candidates.containsKey(candidate)) {
            System.out.println("Invalid candidate.");
            return;
        }

        Block newBlock = new Block(voterId + " votes for " + candidate, blockchain.getLatestBlock().getHash());
        blockchain.addBlock(newBlock);
        voters.add(voterId);
        candidates.put(candidate, candidates.get(candidate) + 1);
        System.out.println("Vote cast successfully for " + candidate);
    }

    public void displayResults() {
        System.out.println("Voting Results:");
        for (Map.Entry<String, Integer> entry : candidates.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public void verifyIntegrity() {
        System.out.println("Blockchain valid: " + blockchain.isChainValid());
    }
}

public class BlockchainVotingApp {
    public static void main(String[] args) {
        VotingSystem votingSystem = new VotingSystem(4);

        votingSystem.castVote("voter1", "Alice");
        votingSystem.castVote("voter2", "Bob");
        votingSystem.castVote("voter1", "Bob"); // Attempt to vote again

        votingSystem.displayResults();
        votingSystem.verifyIntegrity();
    }
}